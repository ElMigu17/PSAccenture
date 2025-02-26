import axios from "axios";
const url = "http://localhost:3000/empresas";
export default {
    createEmpresa(empresa, fornecedores) {
        let dataToSend = this.makeDateToSend(empresa, fornecedores);

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

    getEmpresas() {

        try {
            return axios({
                method: "get",
                url: url,
            })
        } catch (error) {
            console.error("Error sending data:", error);
        }
    },

    deleteEmpresa(id) {
        try {
            return axios.delete(`${url}/${id}`)
        } catch (error) {
            console.error("Error deleting data:", error);
        }
    },

    updateEmpresa(empresa, fornecedores) {
        let dataToSend = this.makeDateToSend(empresa, fornecedores);
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

    makeDateToSend(empresa, fornecedores){


        return {
            id: empresa.id,
            cnpj: empresa.cnpj.replace("-", "").replaceAll(".", "").replace("/", ""),
            nomeFantasia: empresa.nomeFantasia,
            cep: empresa.cep.replace("-", ""),
            estado: empresa.estado,
            fornecedores: fornecedores
        };
    }
}
