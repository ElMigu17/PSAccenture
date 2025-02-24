<script setup></script>

<template>
  <div>
    <button v-on:click="displayForm">Create</button>
  </div>
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
    <div class="my-form">
      <my-form 
      v-model:cnpj="cnpj"
      v-model:nomeFantasia="nomeFantasia"
      v-model:cep="cep"
      @edit-todo="sendForm"/>
    </div>
  </div>
</template>

<script>
import { AgGridVue } from "ag-grid-vue3";
import axios from "axios";
import { themeAlpine } from "ag-grid-community";
import TableButton from "../components/TableButton.vue";
import myForm from "../components/Form.vue";

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
    sendForm(){
      if(this.id == null){
        this.createRow();
      }
      else{
        this.editRow();
      }
    },
    createRow() {
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
          document.getElementsByClassName("my-form")[0].style.display = "none";

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
          this.myRowData = this.myRowData.filter((row) => row.id !== data.id);
        });
      } catch (error) {
        console.error("Error deleting data:", error);
      }
    },
    async editRow() {
      let dataToSend = {
        cnpj: this.cnpj,
        nomeFantasia: this.nomeFantasia,
        cep: this.cep.replace("-", ""),
        id: this.id
      };
      try {
        axios({
          method: "put",
          url: "http://localhost:3000/empresas",
          data: dataToSend,
        }).then((response) => {
          let data = response.data;
          let elementToUpdate = this.myRowData.find((row) => row.id !== data.id);
          console.log(elementToUpdate);
          elementToUpdate.cnpj = data.cnpj;
          elementToUpdate.nomeFantasia = data.nomeFantasia;
          elementToUpdate.cep = data.cep;
          elementToUpdate.id = data.id;
          document.getElementsByClassName("my-form")[0].style.display = "none";

          this.cnpj = "";
          this.nomeFantasia = "";
          this.cep = null;
          this.id = null;
        });
      } catch (error) {
        console.error("Error sending data:", error);
      }
    },
    displayForm(){
      document.getElementsByClassName("my-form")[0].style.display = "block";
    },
    openFormEdit(data){
      this.displayForm();

      console.log(data);
      this.cnpj = data.cnpj;
      this.nomeFantasia = data.nomeFantasia;
      this.cep = data.cep;
      this.id = data.id;

    }
  },
  mounted() {
    this.getData();
  },
};
</script>

<style>
.aggridEmpresa {
  min-width: 80%;
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

.my-form {
  display: none;
}
</style>
