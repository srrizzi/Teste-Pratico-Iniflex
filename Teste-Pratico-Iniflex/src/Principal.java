import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import Pessoa.Funcionario;
import Pessoa.Pessoa;

public class Principal 
{
    //3 – Deve conter uma classe Principal para executar as seguintes ações
    public void main(String[] args) 
    {
        List<Funcionario> funcionarios = new ArrayList<>();

        //Inserir todos os funcionários, na mesma ordem e informações da tabela.
        funcionarios.add(CreateFuncionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(CreateFuncionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(CreateFuncionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(CreateFuncionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(CreateFuncionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(CreateFuncionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(CreateFuncionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(CreateFuncionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(CreateFuncionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(CreateFuncionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        //Remover o funcionário “João” da lista.
        funcionarios.removeIf(f -> f.nome == "João");

        //Imprimir todos os funcionários com todas suas informações, sendo que:
        //• informação de data deve ser exibido no formato dd/mm/aaaa;
        //• informação de valor numérico deve ser exibida no formatado com separador de milhar como ponto e decimal como vírgula.
        System.out.println("========================================================================================");

        System.out.println("Funcionários:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println(retornaFuncionario(funcionario));
        }

        System.out.println("========================================================================================");

        //Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        for(Funcionario func: funcionarios)
        {
            func.salario = func.salario.multiply(new BigDecimal("1.10"));
        }

        //Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
        .collect(Collectors.groupingBy(x -> x.funcao));
 
        //Imprimir os funcionários, agrupados por função.
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);

            for (Funcionario item : lista) {
                System.out.println(retornaFuncionario(item));
            }
            System.out.println("========================================================================================");
        });

        //Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        System.out.println("Aniversariantes dos Meses 10 e 12:");
        funcionarios.stream().filter(x -> x.dataNascimento.getMonthValue() == 10 || x.dataNascimento.getMonthValue() == 12).forEach(func -> {
            System.out.println(retornaFuncionario(func));
        });

        System.out.println("========================================================================================");

        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(x -> x.dataNascimento))
                .orElse(null);

        //Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        if (maisVelho != null) {
            int idade = Period.between(maisVelho.dataNascimento, LocalDate.now()).getYears();
            System.out.println("Funcionário mais velho: " + maisVelho.nome + ", Idade: " + idade);
        }

        System.out.println("========================================================================================");
        
        //Imprimir a lista de funcionários por ordem alfabética.
        System.out.println("Funcionários por ordem alfabética:");
        funcionarios.stream().sorted(Comparator.comparing(x -> x.nome)).forEach(func -> {
            System.out.println(retornaFuncionario(func));      
        });

        System.out.println("========================================================================================");

        BigDecimal totalSalarios = funcionarios.stream()
                .map(x -> x.salario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //Imprimir o total dos salários dos funcionários.
        System.out.println("Total dos salários: " + NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(totalSalarios));

        System.out.println("========================================================================================");

        //Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        System.out.println("Quantos salários mínimos ganha cada funcionário:");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(x -> {
            BigDecimal salariosMinimos = x.salario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(x.nome + " ganha " + salariosMinimos + " salários mínimos.");
        });
    }
    
    private String retornaFuncionario(Funcionario func)
    {
        String retorno = "";

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat numberFormat = new DecimalFormat("#,##0.00");

        String dataNascimentoFormatada = func.dataNascimento.format(dateFormatter);
        String salarioFormatado = numberFormat.format(func.salario);
        retorno = "Nome: " + func.nome + ", Data de Nascimento: " + dataNascimentoFormatada + ", Salário: " + salarioFormatado + ", Função: " + func.funcao;
        return retorno;
    }

    private Funcionario CreateFuncionario(String Nome, LocalDate DataNascimento, BigDecimal Salario, String Funcao)
    {
        Funcionario func = new Funcionario();
        func.nome = Nome;
        func.dataNascimento = DataNascimento;
        func.salario = Salario;
        func.funcao = Funcao;

        return func;
    }
}
