import java.awt.Color;
import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int largura = 700;
        int altura = largura;

        
        Color corCobra = JColorChooser
        .showDialog(null, "Escolha a cor da cobra", Color.GREEN);
        if (corCobra == null) {
            corCobra = Color.GREEN; 
        }

        JFrame janela = new JFrame("Snake Game"); 
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(largura, altura);
        janela.setLocationRelativeTo(null);
        janela.setResizable(false); 

        Game jogo = new Game(largura, altura, corCobra); 
        janela.add(jogo.getPainelJogo()); 

        janela.pack(); 
        janela.setVisible(true); 
        jogo.iniciar(); 
    }
}
