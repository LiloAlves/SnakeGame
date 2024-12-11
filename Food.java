import java.awt.*;
import java.util.Random;

public class Food {
    private Point posicao; 
    private Random geradorAleatorio; 

    public Food(int largura, int altura, int tamanhoBloco) {
        this.geradorAleatorio = new Random();
        gerarNovaPosicao(largura, altura, tamanhoBloco);
    }

    public void gerarNovaPosicao(int largura, int altura, int tamanhoBloco) {
        int x = geradorAleatorio.nextInt(largura / tamanhoBloco);
        int y = geradorAleatorio.nextInt(altura / tamanhoBloco);
        this.posicao = new Point(x, y);
    }

    public Point getPosition() {
        return posicao;
    }

    public void desenhar(Graphics g, int tamanhoBloco) {
        g.setColor(Color.RED);
        g.fill3DRect(posicao.x * tamanhoBloco, posicao.y * tamanhoBloco, tamanhoBloco, tamanhoBloco, true);
    }
}
