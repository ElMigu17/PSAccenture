<script setup></script>

<template >
  <div class="empresaView">
    <div class="creationButton">
      <button v-on:click="displayForm">Create</button>
    </div>
    <div class="positionTable">
      <ag-grid-vue class="aggridEmpresa ag-theme-alpine" :columnDefs="columnDefs" :rowData="myRowData" :theme="theme"
        :defaultColDef="defaultColDef" domLayout="autoHeight">
      </ag-grid-vue>
      <div class="my-form">
        <my-form v-model:cnpj="cnpj" v-model:nomeFantasia="nomeFantasia" v-model:cep="cep"
          v-model:fornecedores="fornecedores" v-model:dataFornecedoresMarcados="dataFornecedoresMarcados"
          @edit-todo="sendForm" @close="closeForm" />
      </div>
    </div>
  </div>
</template>

<script>
import { AgGridVue } from "ag-grid-vue3";
import axios from "axios";
import { themeAlpine } from "ag-grid-community";
import TableButton from "../components/TableButton.vue";
import myForm from "../components/FormEmpresa.vue";

export default {
  components: {
    AgGridVue,
    TableButton,
    myForm,
  },
  data() {
    return {
      cnpj: null,
      nomeFantasia: null,
      cep: null,
      id: null,
      fornecedores: null,
      dataFornecedoresMarcados: [],
      columnDefs: [
        { sortable: true, filter: true, headerName: "CNPJ", field: "cnpj" },
        {
          headerName: "Nome Fantasia",
          field: "nomeFantasia",
          filter: "agTextColumnFilter",
        },
        { headerName: "CEP", field: "cep" },
        { headerName: "Fornecedores", field: "listagemFornecedores" },
        {
          headerName: "Actions",
          cellRenderer: TableButton,
          cellRendererParams: {
            label: "Delete",
            onClick: (data) => this.deleteRow(data),
          },
        },
        {
          headerName: "Actions",
          cellRenderer: TableButton,
          cellRendererParams: {
            label: "Edit",
            onClick: (data) => this.openFormEdit(data),
          },
        },
      ],
      myRowData: [],
      theme: themeAlpine,
    };
  },
  methods: {
    async getData() {
      try {
        axios({
          method: "get",
          url: "http://localhost:3000/empresas",
        }).then((response) => {
          this.myRowData = response.data;
        });
      } catch (error) {
        console.error("Error sending data:", error);
      }
    },
    sendForm() {
      if (this.id == null) {
        this.createRow();
      }
      else {
        this.editRow();
      }
    },
    createRow(event) {
      console.log(this.dataFornecedoresMarcados);
      let dataToSend = {
        cnpj: this.cnpj,
        nomeFantasia: this.nomeFantasia,
        cep: this.cep.replace("-", ""),
        idFornecedores: this.dataFornecedoresMarcados.filter((dfm) => dfm.check).map((dfm) => dfm.id)
      };
      try {
        axios({
          method: "post",
          url: "http://localhost:3000/empresas",
          data: dataToSend,
        }).then((response) => {
          let newLineIndex = this.myRowData.push(response.data);
          let newLine = this.myRowData[newLineIndex-1];
          newLine.listagemFornecedores = this.createListagemFornecedores(newLine.fornecedores);
          this.closeForm();
        });
      } catch (error) {
        console.error("Error sending data:", error);
      }
    },
    async deleteRow(data) {
      try {
        await axios.delete(`http://localhost:3000/empresas/${data.id}`).then((res) => {
          this.myRowData = this.myRowData.filter((row) => row.id !== data.id);
        });
      } catch (error) {
        console.error("Error deleting data:", error);
      }
    },
    async editRow() {
      let fornecedoresFiltrados = this.dataFornecedoresMarcados.filter((dfm) => dfm.check);

      let dataToSend = {
        cnpj: this.cnpj,
        nomeFantasia: this.nomeFantasia,
        cep: this.cep.replace("-", ""),
        id: this.id,
        fornecedores: this.dataFornecedoresMarcados.filter((dfm) => dfm.check).map((dfm) => dfm.id)

      };
      try {
        axios({
          method: "put",
          url: "http://localhost:3000/empresas",
          data: dataToSend,
        }).then((response) => {
          let data = response.data;
          let elementToUpdate = this.myRowData.find((row) => row.id === data.id);
          console.log(elementToUpdate);
          elementToUpdate.listagemFornecedores = this.createListagemFornecedores(dataToSend.fornecedores);
          debugger;
          elementToUpdate.cnpj = data.cnpj;
          elementToUpdate.nomeFantasia = data.nomeFantasia;
          elementToUpdate.cep = data.cep;
          elementToUpdate.id = data.id;
          elementToUpdate.fornecedores = dataToSend.fornecedores;
          this.closeForm();
        });
      } catch (error) {
        console.error("Error sending data:", error);
      }
    },
    displayForm() {

      try {
        axios({
          method: "get",
          url: "http://localhost:3000/fornecedor",
        }).then((response) => {
          this.dataFornecedoresMarcados = JSON.parse(JSON.stringify(this.fornecedoresMarcados(response)));
          console.log("dataFornecedoresMarcados", this.dataFornecedoresMarcados);
          document.getElementsByClassName("my-form")[0].style.display = "block";
          console.log(this.dataFornecedoresMarcados);
        });
      } catch (error) {
        console.error("Error sending data:", error);
      }
    },
    openFormEdit(data) {
      this.fornecedores = data.fornecedores;
      this.displayForm();

      this.cnpj = data.cnpj;
      this.nomeFantasia = data.nomeFantasia;
      this.cep = data.cep;
      this.id = data.id;

    },

    closeForm() {
      this.cnpj = "";
      this.nomeFantasia = "";
      this.cep = null;
      this.id = null;
      this.fornecedores = [];
      this.dataFornecedoresMarcados = [];
      document.getElementsByClassName("my-form")[0].style.display = "none";

    },
    fornecedoresMarcados(allFornecedores) {
      let fornecedoresId = [];
      if (this.fornecedores) {
        for (let forn of this.fornecedores) {
          fornecedoresId.push(Number(forn));
        }
      }
      for (let forn of allFornecedores.data) {
        forn.check = fornecedoresId.includes(forn.id);
      }
      return allFornecedores.data;
    },
    createListagemFornecedores(fornecedoresList){
      
      let listagemFornecedores = "";
      for (let fornecedorIndex in fornecedoresList){
          let fornecedor = this.dataFornecedoresMarcados[fornecedorIndex];
          let document = fornecedor.is_pessoa_fisica ? fornecedor.cpf.toString() : fornecedor.cnpj;
          listagemFornecedores += fornecedor.nome + " - " + document + " ,";
      }
      return listagemFornecedores !== "" ? listagemFornecedores.substring(0, listagemFornecedores.length - 1) : "";
    }
  },
  mounted() {
    this.getData();
  },
};
</script>

<style>
.aggridEmpresa {
  width: 100%;
}

.positionTable {
  width: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
}

.my-form {
  display: none;
}

.empresaView {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100vh;
  padding: 5% 10%;
  align-items: flex-end;
}

button {
    border-radius: 30px;
    margin-top: 15px;
    background-color: black;
    color: white;
    border: 2px solid black;
    padding: 2px 8px;
    cursor: pointer;
  }

</style>
