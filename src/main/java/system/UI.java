package system;

import javax.print.attribute.standard.Sides;
import java.util.*;

public class UI {

    private final String TOPLEFT_S = "╔═";
    private final String TOPRIGHT_S = "═╗";
    private final String BOTTOMLEFT_S = "╚═";
    private final String BOTTOMRIGHT_S = "═╝";
    private final String SIDELEFT_S = "║ ";
    private final String SIDERIGHT_S = " ║";
    private final String SPLITLEFT_S = "╠═";
    private final String SPLITRIGHT_S = "═╣";
    private final String FLAT_S = "═";
    private final String YESCANCEL_B = "[ 1: OK ]  [ 2: ANNULLER ]";

    public UI() {
    }

    public void showMultipleInfoBox(ArrayList<String> list){
        int sizeX=5;
        for(int i=0;i<list.size();i++){ //Looping through all entries in string list
            String[] parts = list.get(i).split("\n"); //Splitting string per new line to string array
            List<String> partssorted = Arrays.asList(parts); //converting string array to list, as sorting is easier
            partssorted.sort(Comparator.comparing(String::length)); //sorting string array by string length
            if(partssorted.get(partssorted.size()-1).length()>sizeX){
                sizeX=partssorted.get(partssorted.size()-1).length(); //Setting size X to the longest string length in array
            }
        }
        String output; //This will be our final print

        //Start with top line
        output = (TOPLEFT_S);
        for(int i=0;i<sizeX;i++){
            output+=FLAT_S;
        }
        output += (TOPRIGHT_S+"\n");


        //The tricky one - One solid box split by lines containing the different entries
        for(int k=0;k<list.size();k++) { //Loop through all entries in array

            //This seems redundant. Double check code to see if partsorted2.size can be replaced with
            //parts.length, and parts2[i].length can be replaced with parts[i].length.
            //Sorting makes no sense here?? We're trying to find the Y length, not X!
            String[] parts = list.get(k).split("\n");
            String[] parts2 = list.get(k).split("\n");
            List<String> partssorted2 = Arrays.asList(parts);
            partssorted2.sort(Comparator.comparing(String::length));

            //Create text
            int sizeY = partssorted2.size();
            for (int i = 0; i < sizeY; i++) {
                output += (SIDELEFT_S + parts2[i]);
                for (int j = 0; j < (sizeX - parts2[i].length()); j++) {
                    output += " ";
                }
                output += SIDERIGHT_S + "\n";
            }


            //Creating split line, if more lines exist afterwards in string array
            if(k!=list.size()-1){
                output += (SPLITLEFT_S);
                for(int i=0;i<sizeX;i++){
                    output+=FLAT_S;
                }
                output += (SPLITRIGHT_S+"\n");
            }
        }
        //Create bottom line
        output += (BOTTOMLEFT_S);
        for(int i=0;i<sizeX;i++){
            output+=FLAT_S;
        }
        output += (BOTTOMRIGHT_S);

        System.out.println(output);

    }

    public void showInfoBox(String message){
        String[] parts = message.split("\n");
        String[] parts2 = message.split("\n");
        List<String> partssorted = Arrays.asList(parts);
        partssorted.sort(Comparator.comparing(String::length));
        int sizeX = partssorted.get(partssorted.size()-1).length();
        int sizeY = partssorted.size();
        if(sizeX<5){
            sizeX=5;
        }
        String output;

        //Start with top line
        output = (TOPLEFT_S);
        for(int i=0;i<sizeX;i++){
            output+=FLAT_S;
        }
        output += (TOPRIGHT_S+"\n"+SIDELEFT_S);

        //Create empty line
        for(int i=0;i<sizeX;i++){
            output+=" ";
        }
        output += (SIDERIGHT_S+"\n");

        //Create text
        for(int i =0;i<sizeY;i++){
            output += (SIDELEFT_S+parts2[i]);
            for(int j=0;j<(sizeX-parts2[i].length());j++){
                output+=" ";
            }
            output+=SIDERIGHT_S+"\n";
        }

        //Create empty line
        output+=SIDELEFT_S;
        for(int i=0;i<sizeX;i++){
            output+=" ";
        }
        output += (SIDERIGHT_S+"\n");

        //Create bottom line
        output += (BOTTOMLEFT_S);
        for(int i=0;i<sizeX;i++){
            output+=FLAT_S;
        }
        output += (BOTTOMRIGHT_S);

        System.out.println(output);

        System.out.print("Please select an option: ");
    }

    public boolean showYesNoDialogBox(String message, Scanner input){
        String[] parts = message.split("\n");
        String[] parts2 = message.split("\n"); //Idk why this has to exist, but for some reason it does.
        List<String> partssorted = Arrays.asList(parts);
        partssorted.sort(Comparator.comparing(String::length));
        int sizeX = partssorted.get(partssorted.size()-1).length();
        int sizeY = partssorted.size();
        if(sizeX<26){
            sizeX=26;
        }
        String output;

        //Start with top line
        output = (TOPLEFT_S);
        for(int i=0;i<sizeX;i++){
            output+=FLAT_S;
        }
        output += (TOPRIGHT_S+"\n"+SIDELEFT_S);

        //Create empty line
        for(int i=0;i<sizeX;i++){
            output+=" ";
        }
        output += (SIDERIGHT_S+"\n");

        //Create text
        for(int i =0;i<sizeY;i++){
            output += (SIDELEFT_S+parts2[i]);
            for(int j=0;j<(sizeX-parts2[i].length());j++){
                output+=" ";
            }
            output+=SIDERIGHT_S+"\n";
        }


        //Create buttons
        output+=SIDELEFT_S + YESCANCEL_B;

        //Finish button line
        for(int j=0;j<(sizeX-YESCANCEL_B.length());j++){
            output+=" ";
        }
        output+=SIDERIGHT_S+"\n";

        //Create bottom line
        output += (BOTTOMLEFT_S);
        for(int i=0;i<sizeX;i++){
            output+=FLAT_S;
        }
        output += (BOTTOMRIGHT_S);

        System.out.println(output);
        System.out.print("Please select an option: ");
        String inputChar = input.nextLine();
        if(inputChar=="1"){
            return true;
        }else if(inputChar=="2"){
            return false;
        }else if(inputChar==""){
            System.out.println("IT WORKS");
            return true;
        }else{
            System.out.println("Ugyldigt input, venligst skriv en af de angivne muligheder.\n");
            return showYesNoDialogBox(message, input);
        }
    }
}
