package test;

import beans.Client;
import beans.Compte;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        System.out.println("TEST");
        Scanner scanner=new Scanner(System.in);
        System.out.println("nom");
        String name=scanner.nextLine();
        System.out.println("solde");
        Double solde=scanner.nextDouble();
        Compte compte=new Compte();
        compte.setSolde(solde);
        Client client=new Client();
        client.setCp(compte);
        client.setNom(name);
        while (true){
            try {
                System.out.println("operation");
                String type=scanner.next();
                System.out.println("montant");
                Double montant=scanner.nextDouble();
                if(type.equals("v")){
                    client.verser(montant);
                }
                else if (type.equals("r")){
                    client.retirer(montant);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


    }
}
