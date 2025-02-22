<script setup></script>

<template>
  <div class="positionTable">
    <ag-grid-vue
      class="aggridEmpresa ag-theme-alpine"
      :columnDefs="columnDefs"
      :rowData="rowData"
      :theme="theme"
      :defaultColDef="defaultColDef"
      domLayout="autoHeight"
    >
    </ag-grid-vue>
    <div>
      <form v-on:submit.prevent="sendForm">
        <p>CNPJ:</p>
        <input type="text" v-model="cnpj">
        <p>Nome Fantasia:</p>
        <input type="text" v-model="nomeFantasia"/>
        <p>CEP:</p>
        <input type="text" v-model="cep"/>
        <input type="submit"/>
      </form>
    </div>
  </div>
</template>

<script>
import { AgGridVue } from "ag-grid-vue3";
import axios from "axios";
import { themeAlpine } from "ag-grid-community";

export default {
  components: {
    AgGridVue,
  },
  data() {
    return {
      cnpj: null,
      nomeFantasia: null,
      cep: null,
      columnDefs: [
        { sortable: true, filter: true, headerName: "Company", field: "company" },
        { headerName: "Model", field: "model", filter: "agTextColumnFilter" },
        { headerName: "Price", field: "price" },
      ],
      rowData: [
        { company: "Toyota", model: "Celica", price: "35000" },
        { company: "Ford", model: "Mondeo", price: "32000" },
        { company: "Porsche", model: "Boxter", price: "72000" },
      ],
      theme: themeAlpine,
    };
  },
  methods: {
    async getData() {
      try {
        axios({
          method: "get",
          url: "http://localhost:3000/empresas",
        }).then(function (response) {
          console.log(response);
        });
      } catch (error) {
        console.error("Error sending data:", error);
      }
    },
    sendForm(){
      let dataToSend = {cnpj: this.cnpj, nomeFantasia: this.nomeFantasia, cep: this.cep};
debugger;
      try {
        axios({
          method: "post",
          url: "http://localhost:3000/empresas",
          data: dataToSend,
        }).then(function (response) {
          console.log(response);
        });
      } catch (error) {
        console.error("Error sending data:", error);
      }
    }
  },
  created() {
    this.getData();
  },
};
</script>

<style>
.aggridEmpresa {
  width: 40%;
}

.positionTable {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  padding-top: 5rem;
}
</style>
