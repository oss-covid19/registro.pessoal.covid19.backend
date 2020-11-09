package covid19.core.util;

/**
 * Obtem um arquivo .HTML com certa marcação: {{ }} e processa.
 *
 * {{hrefRecadastrar}} => http://localhost:8080/covid19/esqueceu-senha/{autorizacaoId}/recadastrar
 * {{autorizacaoId}}   =>
 * Procura no conteudo do arquivo .html dado como path a marcação e trocar por http://localhost:8080/covid19/esqueceu-senha/{{autorizacaoId}}/recadastrar
 *
 *
 */
public class HTMLEmailProcessador {
}
