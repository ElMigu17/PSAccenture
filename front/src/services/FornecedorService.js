import axios from "axios";
const url = "http://localhost:3000/fornecedor";
export default {
    createFornecedor(fornecedor, empresas) {

        let dataToSend = this.makeDateToSend(fornecedor, empresas);

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

        let dataToSend = this.makeDateToSend(fornecedor, empresas);
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

    
    makeDateToSend(fornecedor, empresas){
        let date = null;
        if(fornecedor.data_nascimento != null){
            let d = new Date(fornecedor.data_nascimento)
            date = new Date(d.setTime( d.getTime() + d.getTimezoneOffset()*60*1000 ));
        }
        return {
            id: fornecedor.id,
            nome: fornecedor.nome,
            cnpj: fornecedor.cnpj.replace("-", "").replaceAll(".", "").replace("/", ""),
            cpf: fornecedor.cpf.replaceAll(".", "").replace("-", ""),
            email: fornecedor.email,
            cep: fornecedor.cep.replace("-", ""),
            is_pessoa_fisica: fornecedor.is_pessoa_fisica,
            rg: fornecedor.rg,
            data_nascimento: date,
            listagemEmpresas: fornecedor.listagemEmpresas,
            empresas: empresas
        };
    }
}
