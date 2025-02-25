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
import java.sql.Date;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    private conectaDAO conexao;
    private Connection conn;
       ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public boolean conectar() {

        this.conexao = new conectaDAO();
        this.conn = this.conexao.getConexao();
        if (this.conn == null) {
            System.out.println("Deu problema");
            return false;
        } else {
            return true;
        }
    }

    public int cadastrarProduto(ProdutosDTO produto) {
        if (conn == null) {
            if (!conectar()) {
                return -1; // Código de erro indicando falha na conexão
            }
        }
        int status;
        try {   

            // Preparar a declaração SQL
            String insertQuery = "INSERT INTO produtos(nome, valor, status) VALUES (?,?,?)";
            PreparedStatement st = conn.prepareStatement(insertQuery);

            // Definir os parâmetros da instrução de inserção
            st.setString(1, produto.getNome());
            st.setDouble(2, produto.getValor());
            st.setString(3, produto.getStatus());
            // Supondo que você deseja usar a data atual para data_recebimento

            // Executar a instrução de inserção e obter o status
            status = st.executeUpdate();
            return status;
            
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar para realizar o cadastro " + ex.getMessage());
            return ex.getErrorCode();
        }

    }
    
    public int venderProduto(ProdutosDTO produto) {
        if (conn == null) {
            if (!conectar()) {
                return -1; // Código de erro indicando falha na conexão
            }
        }
        int status;
        try {

            // Instrução SQL para atualizar na tabela entrega com o material_id correspondente
            String updateQuery = "UPDATE produtos SET status = 'Vendido' "
                    + "WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(updateQuery);

            // Definir os parâmetros da instrução de atualização
            st.setInt(1, produto.getId());

            // Executar a declaração de atualização e obter o status
            status = st.executeUpdate();
            return status;

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar para alterar " + ex.getMessage());
            return ex.getErrorCode();
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos(String termoBusca) {
        if (conn == null) {
            if (!conectar()) {
                return null; // Indica falha na conexão
            }
        }
        try {

            List<ProdutosDTO> lista = new ArrayList<>();
            String sqlFiltro = "SELECT id, nome, valor, status \n"
                    + "FROM Produtos\n";
            if (!termoBusca.isEmpty()) {
                sqlFiltro = sqlFiltro + " WHERE status like ?";

            }
            PreparedStatement st = conn.prepareStatement(sqlFiltro);
            if (!termoBusca.isEmpty()) {
                st.setString(1, "%" + termoBusca + "%");
            }
            // armazenar o resultado da pesquisa
            ResultSet rs = st.executeQuery();
            // por ser uma lista usamos o while
            while (rs.next()) {
                ProdutosDTO prodEncontrado = new ProdutosDTO();
                prodEncontrado.setId(rs.getInt("id"));
                prodEncontrado.setNome(rs.getString("nome"));
                prodEncontrado.setValor(rs.getInt("valor"));
                prodEncontrado.setStatus(rs.getString("status"));

                lista.add(prodEncontrado);
            }
            return (ArrayList<ProdutosDTO>) lista;
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar para realizar a listagem " + ex.getMessage());
            return null;
        }
    }

}
