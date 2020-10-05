package src.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import src.Service.AlphabetSoupServices;
import src.models.*;

import java.util.UUID;

@Controller
public class AlphabetSoupController {

    @RequestMapping(method = RequestMethod.POST, value="/alphabetSoup")

    @ResponseBody
    public Object registerAlphabetSoup(
            @RequestParam(value = "w", defaultValue = "15") int w, //columnas
            @RequestParam(value = "h", defaultValue = "15") int h, //filas
            @RequestParam(value = "ltr", defaultValue = "true") boolean ltr, //de izq a derecha
            @RequestParam(value = "rtl", defaultValue = "false") boolean rtl, // de derecha a izq
            @RequestParam(value = "ttb", defaultValue = "true") boolean ttb, // de arriba a abajo
            @RequestParam(value = "btt", defaultValue = "false") boolean btt, // de abajo a arriba
            @RequestParam(value = "d", defaultValue = "false") boolean d)  {  //diagonal

        AlphabetSoupGrid alphabetSoupGridNew = new AlphabetSoupGrid();
        alphabetSoupGridNew.setW(w);
        alphabetSoupGridNew.setH(h);
        alphabetSoupGridNew.setLtr(ltr);
        alphabetSoupGridNew.setRtl(rtl);
        alphabetSoupGridNew.setTtb(ttb);
        alphabetSoupGridNew.setBtt(btt);
        alphabetSoupGridNew.setD(d);

        AlphabetSoupServices inst = AlphabetSoupServices.getInstance();
        UUID uuid = UUID.randomUUID();
        //Se le da valor a la repuesta de la llamada
        if (inst.add(alphabetSoupGridNew, uuid)){
            AlphabetSoupIdJSon stdregreply = new AlphabetSoupIdJSon();
            stdregreply.setId(uuid);
            return stdregreply;
        }else{
            AlphabetSoupMessageJSon stdregreplyW = new AlphabetSoupMessageJSon();
            stdregreplyW.setMessage(inst.getMess());
            return stdregreplyW;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/alphabetSoup/list/{id}")

    @ResponseBody
    public AlphabetSoupListWordsJSon getListWords(@PathVariable UUID id) {
        AlphabetSoupListWordsJSon listW = new AlphabetSoupListWordsJSon();
        listW.setWords(AlphabetSoupServices.getInstance().getListWords(id));
        return listW;
    }

    @RequestMapping(method = RequestMethod.GET, value="/alphabetSoup/view/{id}")

    @ResponseBody
    public String getListWordsFound(@PathVariable UUID id) {
        return AlphabetSoupServices.getInstance().getPrintGrid(id);

    }

    @RequestMapping(method = RequestMethod.PUT, value="/alphabetSoup/{id}")

    @ResponseBody
    public AlphabetSoupMessageJSon findWord(@PathVariable UUID id,
                                       @RequestParam(value = "sr") int sr, //fila inicio
                                       @RequestParam(value = "sc") int sc, //col inicio
                                       @RequestParam(value = "er") int er, //fila fin
                                       @RequestParam(value = "ec") int ec) //col fin
    {

        AlphabetSoupMessageJSon resultWord = new AlphabetSoupMessageJSon();
        resultWord.setMessage(AlphabetSoupServices.getInstance().getFindWords(id,sr,sc,er,ec));
        return resultWord;
    }

}
