import java.awt.*;
import javax.swing.*;

public class Game {
    private final int largura;
    private final int altura;
    private final Color corCobra;

    private GamePanel painelJogo; 

    public Game(int largura, int altura, Color corCobra) {
        this.largura = largura;
        this.altura = altura;
        this.corCobra = corCobra;
        this.painelJogo = new GamePanel(largura, altura, corCobra, this);
    }

    public JPanel getPainelJogo() {
        return painelJogo;
    }

    public void iniciar() {
        painelJogo.requestFocus(); 
    }
}
