package com.lucaslowhandev.literalura.principal;

import com.lucaslowhandev.literalura.service.ConsumoApi;
import com.lucaslowhandev.literalura.service.ConverteDados;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "gutendex.com/books?search=";

}
