package br.com.jpa.api;

import br.com.jpa.dao.Dao;
import br.com.jpa.model.Address;
import br.com.jpa.model.Person;

import java.util.Locale;
import java.util.Scanner;

public class ScreenMenu {
    private final Dao repository = new Dao<>(Person.class);


    public void menuPrincipal() {
        var opcao = " ";
        var entrada = new Scanner(System.in).useLocale(Locale.US);

        do {
            System.out.println();
            System.out.println("\n1 ==> PARA CADASTRAR UMA PESSOA");
            System.out.println("2 ==> PARA LISTAR TODAS AS PESSOAS");
            System.out.println("3 ==> PARA LISTAR UMA PESSOA");
            System.out.println("4 ==> PARA ATUALIZAR UMA PESSOA");
            System.out.println("5 ==> PARA DELETAR UMA PESSOA");
            System.out.println("0 ==> PARA FINALIZAR O APP");

            opcao = entrada.nextLine();

            if (opcao.isBlank()) {
                System.out.println("A ENTRADA NÃO PODE SER EM BRANCO!");
                return;
            }
            switch (opcao) {
                case "1":
                    System.out.println("INFORME O NOME: ");
                    var name = entrada.nextLine();

                    System.out.println("INFORME A IDADE: ");
                    var age = entrada.nextInt();
                    entrada.nextLine();

                    System.out.println("INFORME O CÓDIGO POSTAL: ");
                    var zipCode = entrada.nextLine();

                    System.out.println("INFORME O ENDEREÇO: ");
                    var address = entrada.nextLine();

                    var addressCreated = new Address(zipCode, address);
                    var person = new Person(name, age, addressCreated);

                    repository.save(person);
                    System.out.println("Pessoa cadastrada com sucesso!");
                    break;
                case "2":
                    var allPerson = repository.findAll();
                    System.out.println(allPerson);
                    break;
                case "3":
                    System.out.println("INFORME O ID DA PESSOA");
                    var id = entrada.nextLong();
                    entrada.nextLine();
                    var personById = repository.findById(id);

                    if (personById == null) {
                        System.out.println("Pessoa não encontrada pelo id: " + id + " informado!");
                    } else {
                        System.out.println(personById);
                    }
                    repository.close();
                    break;
                case "4":
                    System.out.println("INFORME O ID DA PESSOA A SER ATUALIZADA");
                    var personId = entrada.nextLong();

                    System.out.println("INFORME A IDADE: ");
                    var newAge = entrada.nextInt();
                    entrada.nextLine();

                    System.out.println("INFORME O NOME: ");
                    var newName = entrada.nextLine();


                    System.out.println("INFORME O ENDEREÇO: ");
                    var newAddress = entrada.nextLine();


                    System.out.println("INFORME O CÓDIGO POSTAL: ");
                    var newZipCode = entrada.nextLine();


                    repository.update(personId, newAge, newName, newZipCode, newAddress);
                    System.out.println("DADOS ATUALIZADOS COM SUCESSO!");
                    repository.close();
                    break;
                case "5":
                    System.out.println("INFORME O ID DA PESSOA A SER DELETADA: ");
                    var deletebyId = entrada.nextLong();

                    repository.delete(deletebyId);
                    entrada.nextLine();
                    repository.close();
                    break;
                case "0":
                    System.out.println("ATÉ LOGO :)");
                    break;
            }
        } while (!opcao.equals("0"));
        entrada.close();
    }
}