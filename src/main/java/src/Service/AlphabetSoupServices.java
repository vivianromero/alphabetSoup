package src.Service;

import src.models.AlphabetSoup;
import src.models.AlphabetSoupGrid;

import java.util.*;
import java.util.stream.Collectors;

public class AlphabetSoupServices {

    private List<AlphabetSoup> soupRecords;

    private static AlphabetSoupServices soupreg = null;

    //orientacion de las parabras
    private static final int HORIZ_LEFT_RIGHT = 0;
    private static final int VERTI_TOP_BOTTOM = 1;
    private static final int DIAGO_BOTTOM_LEFT_RIGHT = 2;
    private static final int DIAGO_TOP_LEFT_RIGHT = 3;
    private static final int HORIZ_RIGHT_LEFT = 4;
    private static final int VERTI_BOTTOM_TOP = 5;
    private static final int DIAG_BOTTOM_RIGHT_LEFT = 6;
    private static final int DIAG_TOP_RIGHT_LEFT = 7;

    //guardar la posicion de las letras de una palabra
    Map<Integer, List<Integer>> posiWord = new HashMap<>();


    private AlphabetSoupServices() {
        soupRecords = new ArrayList<AlphabetSoup>();
    }

    public String mess = "";

    public static AlphabetSoupServices getInstance() {

        if (soupreg == null) {
            soupreg = new AlphabetSoupServices();
            return soupreg;
        } else {
            return soupreg;
        }
    }

    static int[][] dirs;
    int dimDirs;
    static int gridSize;
    List<String> wordsAll;
    List<String> words;
    int[][] valueDirs;

    public boolean add(AlphabetSoupGrid grid, UUID uuid) {
        grid = creaGrid(grid);
        if (grid == null) {
            return false;
        }
        AlphabetSoup soup = new AlphabetSoup(uuid, grid);
        soupRecords.add(soup);
        return true;
    }

    private AlphabetSoupGrid creaGrid(AlphabetSoupGrid grid) {

        int w = grid.getW();
        int h = grid.getH();
        boolean ltr = grid.isLtr();
        boolean rtl = grid.isRtl();
        boolean ttb = grid.isTtb();
        boolean btt = grid.isBtt();
        boolean d = grid.isD();

        if ((w < 15 || h < 15 || w > 80 || h > 80) || !(ltr || rtl || ttb || btt || d)) {
            setMess("Error en los parámetros");
            return null;
        }

        gridSize = w * h;
        dimDirs = 0;
        valueDirs = new int[8][2];

        dimDirs = ltr ? fillDirs(1, 0) : dimDirs; //Horizontal
        dimDirs = ttb ? fillDirs(0, 1) : dimDirs; //Vertical
        dimDirs = d ? fillDirs(1, 1) : dimDirs; //Diagonal hacia abajo de izq a der
        dimDirs = d ? fillDirs(1, -1) : dimDirs; //Diagonal hacia arriba de izq a der
        dimDirs = rtl ? fillDirs(-1, 0) : dimDirs; // Horiz invertida
        dimDirs = btt ? fillDirs(0, -1) : dimDirs; // Vertical invertida
        dimDirs = d ? fillDirs(-1, -1) : dimDirs; // Diagonal hacia abajo de der a izq
        dimDirs = d ? fillDirs(-1, 1) : dimDirs; // Diagonal hacia arriba de der a izq

        dirs = new int[dimDirs][2];
        //llenar dirs
        for (int i = 0; i < dimDirs; i++) {
            dirs[i][0] = valueDirs[i][0];
            dirs[i][1] = valueDirs[i][1];
        }

        wordsAll = new ArrayList<>();
        wordsAll.add("love");
        wordsAll.add("imperium");
        wordsAll.add("hello");
        wordsAll.add("ostrich");
        wordsAll.add("kernel");
        wordsAll.add("Jessica");
        wordsAll.add("computer");
        wordsAll.add("Coffee");
        wordsAll.add("lily");
        wordsAll.add("sand");
        wordsAll.add("aritmetica");
        wordsAll.add("Matematica");
        wordsAll.add("Inspiracion");
        wordsAll.add("estrella");
        wordsAll.add("estrellado");
        wordsAll.add("fuente");
        wordsAll.add("codigo");
        wordsAll.add("enhance");
        wordsAll.add("objetos");
        wordsAll.add("Acoplar");

        grid = Createwordsearch(words, grid);
        return grid;
    }

    private int fillDirs(int value1, int value2) {
        valueDirs[dimDirs][0] = value1;
        valueDirs[dimDirs][1] = value2;
        return ++dimDirs;
    }

    private AlphabetSoupGrid Createwordsearch(List<String> words, AlphabetSoupGrid grid) {
        int width = grid.getW();
        int heigth = grid.getH();
        int numAttempts = 0;

        //se hace un numero de intentos para obtener un Grid con todas las palabras.
        words = wordsAll.stream().
                filter(p -> p.length() >= 3 && p.length() <= Math.max(width, heigth)).
                collect(Collectors.toList());
        words.replaceAll(String::toLowerCase);

        while (++numAttempts < 1000) {
            /*Reorganiza de forma aleatoria las palabras en la lista, para que no siempre se empiece de igual manera,
            da mayor probabilidad a encontrar diferentes Grids para las mismas palabras */
            Collections.shuffle(words);
            //AlphabetSoup.Grid grid = new AlphabetSoup.Grid();
            grid.setWords(words);
            grid.setAlphabetSoup(new char[heigth][width]);
            grid.setSolutions(new ArrayList<>());

            int cellsFilled = 0;


            for (String word : words) {
                cellsFilled += TryPlaceword(grid, word);
                if (grid.getSolutions().size() == words.size()) {
                    grid.setNumAttempts(numAttempts);
                    grid = InsertRandomLetters(grid);
                    return grid;
                }
            }
        }
        setMess("Se ha sobrepasado los 1000 para obtener la grid");
        return null;
    }

    private static int TryPlaceword(AlphabetSoupGrid grid, String word) {
        /*se escoge la direccion y la posicion inicial para la palabra de manera aleatoria
         nuevamente esto aumenta aun mas la probabilidad de tener Grids unicos */

        int randDir = (int) (Math.random() * dirs[0].length);
        int randPos = (int) (Math.random() * gridSize);

        for (int dir = 0; dir < dirs[0].length; dir++) {
            dir = (dir + randDir) % dirs[0].length;

            for (int pos = 0; pos < gridSize; pos++) {
                pos = (pos + randPos) % gridSize;
                int lettersPlaced = TryLocation(grid, word, dir, pos);
                if (lettersPlaced > 0)
                    return lettersPlaced;
            }
        }
        return 0;
    }

    private static int TryLocation(AlphabetSoupGrid grid, String word, int dir, int pos) {

        int nCols = grid.getW();
        int nRows = grid.getH();
        int r = pos / nCols;
        int c = pos % nCols;
        int len = word.length();

        //chequeo de las fronteras, que la palabra quepa desde dicha posicion y direccion.
        if ((dirs[dir][0] == 1 && (len + c) > nCols)
                || (dirs[dir][0] == -1 && (len - 1) > c)
                || (dirs[dir][1] == 1 && (len + r) > nRows)
                || (dirs[dir][1] == -1 && (len - 1) > r)
        )
            return 0;

        int rr, cc, i, overlaps = 0;

        //chequeo de las celdas, que exista una letra ya ubicada en ella y no coincida
        for (i = 0, rr = r, cc = c; i < len; i++) {
            if (grid.getAlphabetSoup()[rr][cc] != '\0' && grid.getAlphabetSoup()[rr][cc] != word.charAt(i)) {
                return 0;
            }

            cc += dirs[dir][0];
            rr += dirs[dir][1];
        }

        //introduccion al grid de la palabra, pueden existir aclopamiento de varias letras para formar diferentes palabras
        for (i = 0, rr = r, cc = c; i < len; i++) {
            if (grid.getAlphabetSoup()[rr][cc] == word.charAt(i)) overlaps++;
            else grid.getAlphabetSoup()[rr][cc] = word.charAt(i);

            if (i < len - 1) {
                cc += dirs[dir][0];
                rr += dirs[dir][1];
            }
        }

        int lettersPlaced = len - overlaps;
        if (lettersPlaced > 0) {
            /*
              esta son las coordenadas donde se encuentran las palabras, columna y fila inicial y final reespectivamente
            son mostradas para hacer mas facil la prueba para ustedes, encotrar palabras cortas en esas sopas no es facil.
             */

            grid.getSolutions().add(String.format("%-20s", word) + "(" + c + "," + r + ")" + "(" + cc + "," + rr + ")");
        }
        return lettersPlaced;
    }

    //Insertan las letras aleatorias que no forman las palabras deseadas.
    private static AlphabetSoupGrid InsertRandomLetters(AlphabetSoupGrid grid) {
        String Alp = "abcdefghijklmnopqrstuvwxyz";
        int nCols = grid.getW();
        int nRows = grid.getH();
        for (int i = 0; i < nRows; i++)
            for (int j = 0; j < nCols; j++)
                if (grid.getAlphabetSoup()[i][j] == '\0')
                    grid.getAlphabetSoup()[i][j] = Alp.charAt((int) Math.random() * (Alp.length() - 1));
        return grid;
    }

    public String upDateSoup(AlphabetSoup soup) {
        AlphabetSoup alphabetSoup = findAlphabetSoup(soup.getId());
        if (alphabetSoup == null) {
            return "Update un-successful";
        }
        soupRecords.set(alphabetSoup.getPosi(), soup);//update the new record
        return "Update successful";
    }

    public List<AlphabetSoup> getSoupRecords() {
        return soupRecords;
    }

    public void setSoupRecords(List<AlphabetSoup> soupRecords) {
        this.soupRecords = soupRecords;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public List<String> getListWords(UUID id) {
        AlphabetSoup soup = findAlphabetSoup(id);
        if (soup == null) {
            return new ArrayList<>();
        }
        return soup.getAlphabetSoupGrid().getSolutions();
    }

    public String getPrintGrid(UUID id) {
        AlphabetSoup soup = findAlphabetSoup(id);
        if (soup == null) {
            return "No existe sopa";
        }
        return printAlphabetSoup(soup.getAlphabetSoupGrid());
    }

    public AlphabetSoup findAlphabetSoup(UUID id) {
        for (int i = 0; i < soupRecords.size(); i++) {
            AlphabetSoup soupr = soupRecords.get(i);
            if (soupr.getId().equals(id)) {
                soupr.setPosi(i);
                return soupr;
            }
        }
        return null;
    }

    public String printAlphabetSoup(AlphabetSoupGrid grid) {
        String result = "";
        int nCols = grid.getW();
        int nRows = grid.getH();
        result = result.concat("    ===== SOPA DE PALABRAS =====\n");
        result = result.concat("\n       ");
        for (int c = 0; c < nCols; c++)
            if (c < 10) result = result.concat(" " + c + " |");
            else result = result.concat(" " + c + "|");

        result = result.concat("\n");

        for (int r = 0; r < nRows; r++) {
            if (r >= 10) result = result.concat("   " + r + "| ");
            else result = result.concat("    " + r + "| ");
            for (int c = 0; c < nCols; c++)
                result = result.concat(" " + grid.getAlphabetSoup()[r][c] + " |");

            result = result.concat("\n");

        }
        result = result.concat("\n\n");
        if (grid.getWordsSolutions().isEmpty()) {
            return result.concat("==== NO HAY PALABRAS ENCONTRADAS ==== \n");
        }
        result = result.concat("==== PALABRAS ENCONTRADAS ==== \n");
        for (String findWord : grid.getWordsSolutions()) {
            result = result.concat(findWord).concat("\n");
        }
        return result;
    }

    private int defineOrientation(int diffRow, int diffCol) {
        if (diffCol == 0 && diffRow == 0) {
            return -1;
        }

        if (diffRow == 0) { //Horizontal
            if (diffCol > 0)
                return 0;
            return 4;
        }
        if (diffCol == 0) { //Horizontal
            if (diffRow < 0)
                return 5;
            return 1;
        }

        if (diffCol > 0 && diffRow > 0) {
            return 2;
        }

        if (diffCol < 0 && diffRow < 0) {
            return 7;
        }

        if (diffCol > 0 && diffRow < 0) {
            return 3;
        }

        if (diffCol < 0 && diffRow > 0) {
            return 6;
        }
        return -1;
    }


    public String getFindWords(UUID id, int sr, int sc, int er, int ec) {

        AlphabetSoup soup = findAlphabetSoup(id);
        int cols = soup.getAlphabetSoupGrid().getW();
        int rows = soup.getAlphabetSoupGrid().getH();

        if (sr >= rows || er >= rows || sc >= cols || ec >= cols || sr < 0 || er < 0 || sc < 0 || ec < 0) {
            return "Error en parámetros";
        }

        int diffRow = er - sr;
        int diffCol = ec - sc;
        int orientation = defineOrientation(diffRow, diffCol);
        if (orientation == -1) {
            return "Error en parámetros";
        }
        return findWord(orientation, sr, sc, er, ec, soup);
    }

    private String findWord(int orientation, int iniFil, int iniCol, int fnFil, int fnCol, AlphabetSoup soup) {
        String word = "";
        AlphabetSoupGrid grid = soup.getAlphabetSoupGrid();
        int key = 0;

        switch (orientation) {
            case HORIZ_LEFT_RIGHT: {
                while (iniCol <= fnCol) {
                    word += getCharGrid(iniFil, iniCol, grid, key++);
                    ++iniCol;
                }
                break;
            }
            case VERTI_TOP_BOTTOM: {
                while (iniFil <= fnFil) {
                    word += grid.getAlphabetSoup()[iniFil][iniCol];
                    ++iniFil;
                }
                break;
            }
            case DIAGO_BOTTOM_LEFT_RIGHT: {
                while (iniCol <= fnCol && fnFil >= iniFil) {
                    word += grid.getAlphabetSoup()[iniFil][iniCol];
                    ++iniFil;
                    ++iniCol;
                }
                break;
            }
            case DIAGO_TOP_LEFT_RIGHT: {
                while (iniCol <= fnCol && fnFil <= iniFil) {
                    word += grid.getAlphabetSoup()[iniFil][iniCol];
                    --iniFil;
                    ++iniCol;
                }
                break;
            }
            case HORIZ_RIGHT_LEFT: {
                while (iniCol >= fnCol) {
                    word += grid.getAlphabetSoup()[iniFil][iniCol];
                    --iniCol;
                }
                break;
            }
            case VERTI_BOTTOM_TOP: {
                while (iniFil >= fnFil) {
                    word += grid.getAlphabetSoup()[iniFil][iniCol];
                    --iniFil;
                }
                break;
            }
            case DIAG_BOTTOM_RIGHT_LEFT: {
                while (iniCol >= fnCol && iniFil >= fnFil) {
                    word += grid.getAlphabetSoup()[iniFil][iniCol];
                    --iniCol;
                    --iniFil;
                }
                break;
            }
            case DIAG_TOP_RIGHT_LEFT: {
                while (iniCol >= fnCol && fnFil >= iniFil) {
                    word += grid.getAlphabetSoup()[iniFil][iniCol];
                    --iniCol;
                    ++iniFil;
                }
                break;
            }
        }
        if (soup.getAlphabetSoupGrid().getWords().contains(word.toLowerCase())) {
            if (!soup.getAlphabetSoupGrid().getWordsSolutions().contains(word.toUpperCase())) {
                soup.getAlphabetSoupGrid().getWordsSolutions().add(word.toUpperCase());
                // actualizar la grid, poner las letras en mayuscula
                Set<Integer> keys = posiWord.keySet();
                for (Integer k : keys) {
                    iniFil = posiWord.get(k).get(0);
                    iniCol = posiWord.get(k).get(1);
                    grid.getAlphabetSoup()[iniFil][iniCol] = (Character.toUpperCase(grid.getAlphabetSoup()[iniFil][iniCol]));
                }
            }
            return "Palabra " + word.toUpperCase() + " encontrada !!!!";
        }
        return word.length() == 0 ? "Revise los parámetros introducidos" : "Palabra " +
                                     word.toLowerCase() + " no está contenida en las palabras a encontrar. ";
    }

    private char getCharGrid(int iniFil, int iniCol, AlphabetSoupGrid grid, int key) {
        List<Integer> posi = new ArrayList<>();
        posi.add(iniFil);
        posi.add(iniCol);
        posiWord.put(key, posi);
        return grid.getAlphabetSoup()[iniFil][iniCol];
    }
}
