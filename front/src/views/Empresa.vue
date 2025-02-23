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
        <input type="text" v-model="cnpj" />
        <p>Nome Fantasia:</p>
        <input type="text" v-model="nomeFantasia" />
        <p>CEP:</p>
        <input
          placeholder="00000-000"
          maxlength="9"
          v-mask="'#####-###'"
          type="text"
          v-model="cep"
        />
        <input type="submit" />
      </form>
    </div>
  </div>
</template>

<script>
import { AgGridVue } from "ag-grid-vue3";
import axios from "axios";
import { themeAlpine } from "ag-grid-community";
import TableButton from "../components/TableButton.vue";
import { toRaw } from "vue";

export default {
  components: {
    AgGridVue,
    TableButton,
  },
  data() {
    return {
      cnpj: null,
      nomeFantasia: null,
      cep: null,
      columnDefs: [
        { sortable: true, filter: true, headerName: "CNPJ", field: "cnpj" },
        {
          headerName: "Nome Fantasia",
          field: "nomeFantasia",
          filter: "agTextColumnFilter",
        },
        { headerName: "CEP", field: "cep" },
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
            onClick: (data) => this.editRow(data),
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
    sendForm(event) {
      let dataToSend = {
        cnpj: this.cnpj,
        nomeFantasia: this.nomeFantasia,
        cep: this.cep.replace("-", ""),
      };
      try {
        axios({
          method: "post",
          url: "http://localhost:3000/empresas",
          data: dataToSend,
        }).then((response) => {
          this.myRowData.push(response.data);
          console.log(event);
          event.target.reset();

          this.cnpj = "";
          this.nomeFantasia = "";
          this.cep = null;
        });
      } catch (error) {
        console.error("Error sending data:", error);
      }
    },
    async deleteRow(data) {
      try {
        await axios.delete(`http://localhost:3000/empresas/${data.id}`).then((res) => {
          console.log(this.myRowData);
          this.myRowData = this.myRowData
            .filter((row) => row.id !== data.id);
        });
      } catch (error) {
        console.error("Error deleting data:", error);
      }
    },
    async editRow(data) {
      try {
        axios({
          method: "edit",
          url: "http://localhost:3000/empresas",
          data: dataToSend,
        }).then((response) => {
          this.myRowData.push(response.data);
          console.log(event);
          event.target.reset();

          this.cnpj = "";
          this.nomeFantasia = "";
          this.cep = null;
        });
      } catch (error) {
        console.error("Error deleting data:", error);
      }
    },
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
