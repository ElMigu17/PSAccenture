import axios from "axios";

export default {
    verificarCep(cep) {
        return axios({
            method: 'get',
            url: `https://viacep.com.br/ws/${cep}/json/`,
        });
    }
}