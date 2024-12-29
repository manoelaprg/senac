/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet rs;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    private String url = "jdbc:mysql://localhost:3306/uc11?useTimezone=true&serverTimezone=UTC";
    private String user = "root";
    private String password = "manoela1998!";

    public boolean conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            return true;

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Falha na conexão" + ex.getMessage());
            return false;
        }
    }
    
    public void desconectar() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexão fechada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }
    

    public void cadastrarProduto(ProdutosDTO p) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

        try {
            if (conectar()) {
                prep = conn.prepareStatement(sql);
                prep.setString(1, p.getNome());
                prep.setDouble(2, p.getValor());
                prep.setString(3, p.getStatus());

                int result = prep.executeUpdate();

                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Falha na conexão");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro SQL" + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao fechar conexão");
            }
        }

    }

    public List<ProdutosDTO> listarProdutos() {
        List<ProdutosDTO> listagem = new ArrayList<>();
        try {
            prep = conn.prepareStatement("SELECT * FROM produtos");
            rs = prep.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("Nenhum produto encontrado.");
            } else {
                while (rs.next()) {
                    ProdutosDTO p = new ProdutosDTO();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setValor(rs.getInt("valor"));
                    p.setStatus(rs.getString("status"));
                    
                    listagem.add(p);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao consultar produtos: " + ex.getMessage());
        }

        return listagem;
    }

    public void produtosLista() {
        List<ProdutosDTO> lista = listarProdutos();

        for (ProdutosDTO p : lista) {
            System.out.println("ID: " + p.getId());
            System.out.println("Nome: " + p.getNome());
            System.out.println("Valor: " + p.getValor());
            System.out.println("Status: " + p.getStatus());
        }
    }

   
}
