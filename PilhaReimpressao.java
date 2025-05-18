import java.util.ArrayList;

public class PilhaReimpressao {
    private ArrayList<Documento> documentos;
    private Pilha indicadorPilha;
    private int capacidade;
    private int tamanho;

    public PilhaReimpressao(int capacidade) {
        this.capacidade = capacidade;
        this.tamanho = 0;
        this.documentos = new ArrayList<>(capacidade);
        this.indicadorPilha = new Pilha();
    }

    public boolean pilhaVazia() {
        return indicadorPilha.pilhaVazia();
    }

    public boolean pilhaCheia() {
        return tamanho >= capacidade;
    }

    public void adicionarDocumento(Documento doc) {
        if (pilhaCheia())
            throw new RuntimeException("Pilha de reimpressão cheia");

        documentos.add(doc);
        indicadorPilha.push((char) tamanho);
        tamanho++;
    }

    public Documento reimprimirDocumento() {
        if (pilhaVazia())
            throw new RuntimeException("Pilha de reimpressão vazia");

        int indice = (int) indicadorPilha.pop();
        Documento doc = documentos.get(indice);
        tamanho--;
        return doc;
    }

    public Documento consultarDocumento(String nomeArquivo) {
        for (Documento doc : documentos) {
            if (doc.getNomeArquivo().equals(nomeArquivo)) {
                return doc;
            }
        }
        throw new DocumentoNaoEncontradoException(nomeArquivo);
    }

    public String gerarRelatorio() {
        if (pilhaVazia()) {
            return "Pilha de reimpressão vazia.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Relatório da Pilha de Reimpressão:\n");
        sb.append("Ocupação: ").append(tamanho).append("/").append(capacidade).append("\n");
        sb.append("Documentos na pilha: (do topo para baixo) \n");

        Pilha tempPilha = new Pilha();
        Pilha auxPilha = new Pilha();

        while (!indicadorPilha.pilhaVazia()) {
            char indice = indicadorPilha.pop();
            auxPilha.push(indice);
        }

        int posicao = 1;
        while (!auxPilha.pilhaVazia()) {
            char indice = auxPilha.pop();
            indicadorPilha.push(indice);
            tempPilha.push(indice);
            Documento doc = documentos.get((int) indice);
            sb.append(posicao++).append(". ").append(doc.toString()).append("\n");
        }
        return sb.toString();
    }

    public class DocumentoNaoEncontradoException extends RuntimeException {
        public DocumentoNaoEncontradoException(String nomeArquivo) {
            super("Documento não encontrado: " + nomeArquivo);
        }
    }
}