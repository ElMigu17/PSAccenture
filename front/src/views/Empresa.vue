<script setup></script>

<template>
  <div class="empresaView">
    <div class="creationButton">
      <button v-on:click="displayForm">Create</button>
    </div>
    <div class="positionTable">
      <ag-grid-vue class="aggridEmpresa ag-theme-alpine" :columnDefs="columnDefs" :rowData="myRowData" :theme="theme"
        :defaultColDef="defaultColDef" domLayout="autoHeight">
      </ag-grid-vue>
      <div class="my-form">
        <my-form 
          v-model:dataFornecedoresMarcados="dataFornecedoresMarcados"
          v-model:empresaManipulated="empresaManipulated" @edit-todo="sendForm" @close="closeForm" />
      </div>
    </div>
  </div>
</template>

<script>
import { AgGridVue } from "ag-grid-vue3";
import { themeAlpine } from "ag-grid-community";
import TableButton from "../components/TableButton.vue";
import myForm from "../components/FormEmpresa.vue";
import EmpresaService from "../services/EmpresaService.js"
import FornecedorService from "../services/FornecedorService.js"

export default {
  components: {
    AgGridVue,
    TableButton,
    myForm,
  },
  data() {
    return {
      empresaManipulated: {
        cnpj: null,
        nomeFantasia: null,
        cep: null,
        id: null,
        fornecedores: null,
      },
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
      EmpresaService.getEmpresas().then((response) => {
        this.myRowData = response.data;
      });
    },
    sendForm() {
      if (this.empresaManipulated.id == null) {
        this.createRow();
      }
      else {
        this.editRow();
      }
    },
    createRow(event) {
      let fornecedores = this.dataFornecedoresMarcados.filter((dfm) => dfm.check).map((dfm) => dfm.id);

      EmpresaService.createEmpresa(this.empresaManipulated, fornecedores)
        .then((response) => {
          let newLineIndex = this.myRowData.push(response.data);
          let newLine = this.myRowData[newLineIndex - 1];
          newLine.listagemFornecedores = this.createListagemFornecedores(fornecedores);
          this.closeForm();
        })
        .catch((err) => {
          alert(err.response.data.message);
        });
    },
    async deleteRow(data) {
      EmpresaService.deleteEmpresa(data.id).then((res) => {
        this.myRowData = this.myRowData.filter((row) => row.id !== data.id);
      });
    },
    async editRow() {
      let fornecedores = this.dataFornecedoresMarcados.filter((dfm) => dfm.check).map((dfm) => dfm.id)

      EmpresaService.updateEmpresa(this.empresaManipulated, fornecedores)
        .then((response) => {
          let data = response.data;
          let elementToUpdate = this.myRowData.find((row) => row.id === data.id);

          elementToUpdate.cnpj = data.cnpj;
          elementToUpdate.nomeFantasia = data.nomeFantasia;
          elementToUpdate.cep = data.cep;
          elementToUpdate.id = data.id;
          elementToUpdate.fornecedores = fornecedores;
          elementToUpdate.listagemFornecedores = this.createListagemFornecedores(fornecedores);
          this.closeForm();
        })
        .catch((err) => {
          alert(err.response.data.message);
        });
    },
    displayForm() {

      FornecedorService.getFornecedor().then((response) => {
        this.dataFornecedoresMarcados = JSON.parse(JSON.stringify(this.fornecedoresMarcados(response)));
        document.getElementsByClassName("my-form")[0].style.display = "block";
      });
    },
    openFormEdit(data) {
      this.empresaManipulated.fornecedores = data.fornecedores;
      this.displayForm();

      this.empresaManipulated.cnpj = data.cnpj;
      this.empresaManipulated.nomeFantasia = data.nomeFantasia;
      this.empresaManipulated.cep = data.cep;
      this.empresaManipulated.id = data.id;

    },

    closeForm() {
      this.empresaManipulated.cnpj = "";
      this.empresaManipulated.nomeFantasia = "";
      this.empresaManipulated.cep = null;
      this.empresaManipulated.id = null;
      this.empresaManipulated.fornecedores = [];
      this.dataFornecedoresMarcados = [];
      document.getElementsByClassName("my-form")[0].style.display = "none";

    },
    fornecedoresMarcados(allFornecedores) {
      let fornecedoresId = [];
      if (this.empresaManipulated.fornecedores) {
        for (let forn of this.empresaManipulated.fornecedores) {
          fornecedoresId.push(Number(forn));
        }
      }
      for (let forn of allFornecedores.data) {
        forn.check = fornecedoresId.includes(forn.id);
      }
      return allFornecedores.data;
    },
    createListagemFornecedores(fornecedoresList) {

      let listagemFornecedores = "";
      for (let fornecedorIndex in fornecedoresList) {
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
