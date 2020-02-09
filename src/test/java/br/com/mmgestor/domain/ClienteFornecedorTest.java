package br.com.mmgestor.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import br.com.mmgestor.web.rest.TestUtil;

public class ClienteFornecedorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteFornecedor.class);
        ClienteFornecedor clienteFornecedor1 = new ClienteFornecedor();
        clienteFornecedor1.setId(1L);
        ClienteFornecedor clienteFornecedor2 = new ClienteFornecedor();
        clienteFornecedor2.setId(clienteFornecedor1.getId());
        assertThat(clienteFornecedor1).isEqualTo(clienteFornecedor2);
        clienteFornecedor2.setId(2L);
        assertThat(clienteFornecedor1).isNotEqualTo(clienteFornecedor2);
        clienteFornecedor1.setId(null);
        assertThat(clienteFornecedor1).isNotEqualTo(clienteFornecedor2);
    }
}
