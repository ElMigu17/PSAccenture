import axios from "axios";
const url = "http://localhost:3000/empresas";
export default {
    createEmpresa(empresa, fornecedores) {

        let dataToSend = {
            cnpj: empresa.cnpj,
            nomeFantasia: empresa.nomeFantasia,
            cep: empresa.cep.replace("-", ""),
            fornecedores: fornecedores
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

        let dataToSend = {
            id: empresa.id,
            cnpj: empresa.cnpj,
            nomeFantasia: empresa.nomeFantasia,
            cep: empresa.cep.replace("-", ""),
            fornecedores: fornecedores
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
