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
  </div>
</template>

<script>
import { AgGridVue } from "ag-grid-vue3";
import axios from 'axios';
import { themeAlpine } from 'ag-grid-community';


export default {
  components: {
    AgGridVue,
  },
  data() {
    return {
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
            console.error('Error sending data:', error);
          }
        }
    },
    created(){
        this.getData();
    },
};
</script>

<style>
.aggridEmpresa {
  width: 40%;
}

.positionTable{
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding-top: 5rem;
}
</style>
