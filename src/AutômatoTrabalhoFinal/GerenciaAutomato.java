package AutômatoTrabalhoFinal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author gustavo
 */

public class GerenciaAutomato {

    private String[][] tabelaTransicao;
    private String [] estadosFinais;
    private List<String> e;
    private List<String> a;
    private boolean isDeterministico;

    public String[][] getTabelaTransicao() {
        return tabelaTransicao;
    }

    public String[] getEstadosFinais() {
        return estadosFinais;
    }

    public List<String> getE() {
        return e;
    }

    public List<String> getA() {
        return a;
    }
    
    public void setTabelaTam(int x, int y) {
        tabelaTransicao = new String[x][y];
        limparTabela(tabelaTransicao); 
    }

    public boolean setTransitions(ArrayList<Transitions> t,
        ArrayList<String> e, ArrayList<String> a,  String [] estadosFinais) {
        boolean mValorado = false;
        isDeterministico = true;
        
        //armazena na classe para testar sentença depois
        this.e = e;
        this.a = a;
        this.estadosFinais = estadosFinais;
        
        setTabelaTam(e.size(), a.size());
        
        for (Transitions tran : t) {
            
            if (!tabelaTransicao[e.indexOf(tran.getEstadoI())]
                    [a.indexOf(tran.getSimbolo())].equals("")) {
                mValorado = true;
                isDeterministico = false;
            }

            tabelaTransicao[e.indexOf(tran.getEstadoI())]
                    [a.indexOf(tran.getSimbolo())]
                    += (mValorado? ";" : "") + (tran.getEstadoF());
            
            mValorado = false;              
        }
        
        exibirTabela();   
                 
        if(!isDeterministico){
           tranformInAFND(tabelaTransicao);   
        }     
        
        return isDeterministico;
    }

    public void tranformInAFND(String[][] tabelaTransicao){
        String currentE;  
        List<String> estadosFinaisAux = new ArrayList<>();
        List<String> eAux = new ArrayList<>();
                     
        eAux.add(e.get(0));
      
        //verifica quantidade de novos estados e os armazena
        for (int i = 0; i < eAux.size(); i++) {
            for (int j = 0; j < a.size(); j++) {             
                currentE = transitionsOf(eAux.get(i).split(";"), j);           
                
                if(!currentE.equals("") && !eAux.contains(currentE)){
                    eAux.add(currentE);
                    if(isFinal(currentE)) estadosFinaisAux.add(currentE);
                }
            }
        }      
        
        String [][] tabelaAFD = new String[eAux.size()][tabelaTransicao[0].length];
        
        limparTabela(tabelaAFD); 
        
        for (int i = 0; i < eAux.size(); i++) {
            for (int indexSim = 0; indexSim < a.size(); indexSim++) {
                tabelaAFD[i][indexSim] = transitionsOf(eAux.get(i).split(";"), indexSim);
            }
        }         
        
        e = eAux;
        
        //atualiza tabela
        this.tabelaTransicao = new String[tabelaAFD.length][tabelaAFD[0].length];
        this.tabelaTransicao = tabelaAFD;
       
        //atualiza estados finais
        estadosFinais = new String[estadosFinaisAux.size()];
        estadosFinais = estadosFinaisAux.toArray(estadosFinais);
        
        exibirTabela();   
    }
    
    public String transitionsOf(String[] eAux, int indexSim){
        String newEstado = "";
        
        for (int i = 0; i < eAux.length; i++) {
            newEstado = uniao(newEstado.split(";"), tabelaTransicao[e.indexOf(eAux[i])][indexSim].split(";"));
            
            if(!newEstado.endsWith(";") && !newEstado.equals("")) newEstado+=";";
        }      
        
        if(!newEstado.equals(""))
            newEstado = newEstado.substring(0, newEstado.length()-1);
              
        return ordenar(newEstado.split(";")); 
    }
    
    //faz a junção de duas String sem existir repetições
    public String uniao(String[] a, String[] b) {
        String retorno = "";

        for (int i = 0; i < a.length; i++) {
            retorno += a[i] + ";";
            for (int j = 0; j < b.length; j++) {
                if (!a[i].equals(b[j]) && !retorno.contains(b[j])) {
                    retorno += b[j] + ";";
                }
            }
        }

        if (retorno.startsWith(";")) retorno = retorno.substring(1, retorno.length());
        if (retorno.endsWith(";")) retorno =retorno.substring(0, retorno.length()-1);
        
        return retorno;
    }
    
    public String ordenar(String[] estado){
        String ordenado = "";
        
        Arrays.sort(estado);
             
        for (String estado1 : estado) {
            ordenado += estado1 + ";";
        }
            
        return ordenado.substring(0, ordenado.length() - 1);
    }
    
    public boolean isFinal(String estado){
        System.out.println("EF");
        for (int i = 0; i < estadosFinais.length; i++) {
            System.out.println(estadosFinais[i]);
            if(estado.contains(estadosFinais[i])){
                        System.out.println("-----");

                return true;
            }
        }
        System.out.println("-----");
        return false;
    }
    
                                          //recebe o inicial
    public boolean testarSent(String fita, String estado) {
        char caracter;

        for (int i = 0; i < fita.length(); i++) {
            caracter = fita.charAt(i);
            try {
                if (!tabelaTransicao[e.indexOf(estado)][a.indexOf(caracter + "")].equals("")) {
                    estado = tabelaTransicao[e.indexOf(estado)][a.indexOf(caracter + "")];
                } else {
                    return false;
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                return false;
            }
        }
        return Arrays.asList(estadosFinais).contains(estado);

    }
        
    public void exibirTabela() {
        System.out.println("---------------------");
        for (int i = 0; i < tabelaTransicao.length; i++) {
            for (int l = 0; l < tabelaTransicao[0].length; l++) {
                System.out.print(tabelaTransicao[i][l] + "-");
            }
            System.out.println();
        }
    }
    
    public void limparTabela(String [][] tebela){
        for(int i=0; i< tebela.length; i++){
            for (int j = 0; j < tebela[0].length; j++) {
                tebela[i][j] = "";
            }
        }
    }
    
    public String getGramatica() {
        String transition = "";
        String gramatica = "";
        boolean setVazio = false;
        
        for (int i = 0; i < e.size(); i++) {
            
            for (int j = 0; j < a.size(); j++) {
                if(!tabelaTransicao[i][j].equals("")){
                    transition += String.format("%s%s|", a.get(j), tabelaTransicao[i][j]);
                    
                    if(isFinal(tabelaTransicao[i][j]))
                        transition += a.get(j) + "|";
                }else{
                    if(isFinal(e.get(i))){
                        System.out.println("c "  + e.get(i) );
                        setVazio = true;
                    }
                }
            }
            if(setVazio)
                transition += "î";
            
            if(!transition.equals("")){
                 gramatica += String.format("%s -> %s\n", e.get(i), transition);
            }
            
            transition = "";
            setVazio = false;
        }
        
        return gramatica;
    }
}    
       