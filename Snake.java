import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Point> corpo; 
    private int velocidadeX, velocidadeY; 
    private Color cor; 
    private boolean crescendo = false; 

    public Snake(int posicaoInicialX, int posicaoInicialY, Color cor) {
        this.corpo = new ArrayList<>();
        this.corpo.add(new Point(posicaoInicialX, posicaoInicialY)); 
        this.velocidadeX = 0;
        this.velocidadeY = 0;
        this.cor = cor;
    }

    public void mover() {
        Point cabeca = getCabeca();
        Point novaCabeca = new Point(cabeca.x + velocidadeX, cabeca.y + velocidadeY);
        corpo.add(0, novaCabeca); 

        if (!crescendo) {
            corpo.remove(corpo.size() - 1); 
        } else {
            crescendo = false;
        }
    }

    public void crescer() {
        crescendo = true; 
    }

    public boolean verificarColisao(Point outro) {
        for (Point parte : corpo) {
            if (parte.equals(outro)) {
                return true;
            }
        }
        return false;
    }

    public Point getCabeca() {
        return corpo.get(0); 
    }

    public void definirDirecao(int velocidadeX, int velocidadeY) {
       

        if ((this.velocidadeX != 0 && velocidadeX == -this.velocidadeX) ||
            (this.velocidadeY != 0 && velocidadeY == -this.velocidadeY)) {
                
            return;
        }
        this.velocidadeX = velocidadeX;
        this.velocidadeY = velocidadeY;
    }

    public void desenhar(Graphics g, int tamanhoBloco) {
        g.setColor(cor);
        for (Point parte : corpo) {
            g.fill3DRect(parte.x * tamanhoBloco, parte.y * tamanhoBloco, tamanhoBloco, tamanhoBloco, true);
        }
    }

    public boolean saiuDosLimites(int largura, int altura, int tamanhoBloco) {
        Point cabeca = getCabeca();
        return cabeca.x < 0 || cabeca.x >= largura / tamanhoBloco || cabeca.y < 0 || cabeca.y >= altura / tamanhoBloco;
    }

    public int getVelocidadeX() {
        return velocidadeX;
    }

    public int getVelocidadeY() {
        return velocidadeY;
    }

    public ArrayList<Point> getCorpo() {
        return corpo;
    }

    public Color getColor() {
        return cor;
    }
}
