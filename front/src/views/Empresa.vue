<script setup></script>

<template>
  <div class="positionTable">
    <ag-grid-vue
      class="aggridEmpresa ag-theme-alpine"
      :columnDefs="columnDefs"
      :rowData="myRowData"
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
        { sortable: true, filter: true, headerName: "CNPJ", field: "cnpj" },
        { headerName: "Nome Fantasia", field: "nomeFantasia", filter: "agTextColumnFilter" },
        { headerName: "CEP", field: "cep" },
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
    sendForm(){
      let dataToSend = {cnpj: this.cnpj, nomeFantasia: this.nomeFantasia, cep: this.cep};
      try {
        axios({
          method: "post",
          url: "http://localhost:3000/empresas",
          data: dataToSend,
        }).then( (response) => {
          console.log(response);
        });
      } catch (error) {
        console.error("Error sending data:", error);
      }
      this.getData();
    }
  },
  mounted() {
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
