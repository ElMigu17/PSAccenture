import axios from "axios";
const url = "http://localhost:3000/fornecedor";
export default {
    createFornecedor(fornecedor, empresas) {

        let dataToSend = {
            cnpj: fornecedor.cnpj,
            cpf: fornecedor.cpf,
            nome: fornecedor.nome,
            email: fornecedor.email,
            cep: fornecedor.cep.replace("-", ""),
            is_pessoa_fisica: fornecedor.is_pessoa_fisica,
            rg: fornecedor.rg,
            data_nascimento: fornecedor.data_nascimento,
            listagemEmpresas: fornecedor.listagemEmpresas,
            empresas: empresas
        };

        try {
            return axios({
                method: "post",
                url: url,
                data: dataToSend,
            });
        } catch (error) {
            console.error("Error sending data:", error);
        }
    },

    getFornecedor() {

        try {
            return axios({
                method: "get",
                url: url,
            })
        } catch (error) {
            console.error("Error sending data:", error);
        }
    },

    deleteFornecedor(id) {
        try {
            return axios.delete(`${url}/${id}`)
        } catch (error) {
            console.error("Error deleting data:", error);
        }
    },

    updateFornecedor(fornecedor, empresas) {

        let dataToSend = {
            id: fornecedor.id,
            cnpj: fornecedor.cnpj,
            cpf: fornecedor.cpf,
            nome: fornecedor.nome,
            email: fornecedor.email,
            cep: fornecedor.cep.replace("-", ""),
            is_pessoa_fisica: fornecedor.is_pessoa_fisica,
            rg: fornecedor.rg,
            data_nascimento: fornecedor.data_nascimento,
            listagemEmpresas: fornecedor.listagemEmpresas,
            empresas: empresas
        };

        try {
            return axios({
                method: "put",
                url: url,
                data: dataToSend,
            });
        } catch (error) {
            console.error("Error sending data:", error);
        }
    },
}
