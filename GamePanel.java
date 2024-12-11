import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int largura;
    private final int altura;
    private final int tamanhoBloco = 25;

    private Snake cobra; 
    private Food comida; 
    private Timer temporizador; 
    private boolean jogoEncerrado = false; 
    private JButton botaoReiniciar; 


    public GamePanel(int largura, int altura, Color corCobra, Game game) {
        this.largura = largura;
        this.altura = altura;
        this.cobra = new Snake(5, 5, corCobra);
        this.comida = new Food(largura, altura, tamanhoBloco);

        setPreferredSize(new Dimension(largura, altura + 25)); 
        setBackground(Color.BLACK);
        setFocusable(true); 
        addKeyListener(this);

        temporizador = new Timer(120, this); 
        temporizador.start();

        
        botaoReiniciar = new JButton("Reiniciar");
        botaoReiniciar.setFocusable(false); 
        botaoReiniciar.addActionListener(e -> reiniciarJogo());
        setLayout(new BorderLayout());
        add(botaoReiniciar, BorderLayout.SOUTH);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!jogoEncerrado) {
            desenharJogo(g);
        } else {
            desenharTelaGameOver(g);
        }
    }

    private void desenharJogo(Graphics g) {
        comida.desenhar(g, tamanhoBloco);
        cobra.desenhar(g, tamanhoBloco);

        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Pontuação: " + obterPontuacao(), 10, 20);
    }

    private void desenharTelaGameOver(Graphics g) {
        
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        String mensagemGameOver = "Game Over";

        String mensagemPontuacao = "Pontuação Final: " + obterPontuacao();
        FontMetrics fm = g.getFontMetrics();
        int x = (largura - fm.stringWidth(mensagemGameOver)) / 2;
        int y = altura / 2 - 20;
        g.drawString(mensagemGameOver, x, y);
        x = (largura - fm.stringWidth(mensagemPontuacao)) / 2;
        g.drawString(mensagemPontuacao, x, y + 40);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!jogoEncerrado) {
            if (cobraIniciouMovimento()) {
                cobra.mover();

               
                if (cobra.verificarColisao(comida.getPosition())) {
                    cobra.crescer();
                    comida.gerarNovaPosicao(largura, altura, tamanhoBloco);
                }

                if (obterPontuacao() == 5 ) {
                    temporizador.setDelay(100); 
                    temporizador.start();
                }

                if (obterPontuacao() == 10 ) {
                    temporizador.setDelay(80); 
                    temporizador.start();
                }

                if (obterPontuacao() == 15 ) {
                    temporizador.setDelay(50); 
                    temporizador.start();
                }
                
                
                if (verificarColisaoComCorpo() || cobra.saiuDosLimites(largura, altura, tamanhoBloco)) {
                    jogoEncerrado = true;
                    temporizador.stop();
                }

            }

            repaint(); 
        }
    }

    private boolean cobraIniciouMovimento() {
        return cobra.getVelocidadeX() != 0 || cobra.getVelocidadeY() != 0;
    }

    private boolean verificarColisaoComCorpo() {
        Point cabeca = cobra.getCabeca();
        for (int i = 1; i < cobra.getCorpo().size(); i++) { 
            if (cabeca.equals(cobra.getCorpo().get(i))) {
                return true;
            }
        }
        return false;
    }

    private int obterPontuacao() {
        return cobra.getCorpo().size() - 1;
    }

    public void reiniciarJogo() {
        jogoEncerrado = false;
        cobra = new Snake(5, 5, cobra.getColor());
        comida = new Food(largura, altura, tamanhoBloco);
        temporizador.setDelay(120); 
        temporizador.restart();
        requestFocus(); 
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> cobra.definirDirecao(0, -1);
            case KeyEvent.VK_DOWN -> cobra.definirDirecao(0, 1);
            case KeyEvent.VK_LEFT -> cobra.definirDirecao(-1, 0);
            case KeyEvent.VK_RIGHT -> cobra.definirDirecao(1, 0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
