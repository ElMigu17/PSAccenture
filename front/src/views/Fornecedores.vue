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
        <my-form v-model:dataEmpresasMarcados="dataEmpresasMarcados"
          v-model:fornecedorManipulated="fornecedorManipulated" @edit-todo="sendForm" @close="closeForm" />
      </div>
    </div>
  </div>
</template>

<script>
import { AgGridVue } from "ag-grid-vue3";
import { themeAlpine } from "ag-grid-community";
import TableButton from "../components/TableButton.vue";
import myForm from "../components/FormFornecedor.vue";
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
      fornecedorManipulated: {
        cnpj: null,
        cpf: null,
        nome: null,
        email: null,
        cep: null,
        id: null,
        is_pessoa_fisica: false,
        rg: null,
        data_nascimento: null,
        listagemEmpresas: null,
        empresas: null,
      },
      dataEmpresasMarcados: [],
      columnDefs: [
        { sortable: true, filter: true, headerName: "CNPJ", field: "cnpj" },
        { sortable: true, filter: true, headerName: "CPF", field: "cpf" },
        {
          headerName: "Nome",
          field: "nome",
          filter: "agTextColumnFilter",
        },
        { headerName: "E-mail", field: "email" },
        { headerName: "CEP", field: "cep" },
        { headerName: "RG", field: "rg" },
        { headerName: "Data de nascimento", field: "data_nascimento" },
        { headerName: "Empresas", field: "listagemEmpresas" },
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
      FornecedorService.getFornecedor().then((response) => {
        this.myRowData = response.data;
      });
    },
    sendForm() {
      if (this.fornecedorManipulated.id == null) {
        this.createRow();
      }
      else {
        this.editRow();
      }
    },
    createRow(event) {
      let empresas = this.dataEmpresasMarcados.filter((dfm) => dfm.check).map((dfm) => dfm.id);

      FornecedorService.createFornecedor(this.fornecedorManipulated, empresas)
        .then((response) => {
          let newLineIndex = this.myRowData.push(response.data);
          let newLine = this.myRowData[newLineIndex - 1];
          newLine.listagemEmpresas = this.createListagemEmpresas(empresas);
          this.closeForm();
        })
        .catch((err) => {
          alert(err.response.data.message);
        });
    },
    async deleteRow(data) {
      FornecedorService.deleteFornecedor(data.id).then((res) => {
        this.myRowData = this.myRowData.filter((row) => row.id !== data.id);
      });
    },
    async editRow() {
      let empresas = this.dataEmpresasMarcados.filter((dfm) => dfm.check).map((dfm) => dfm.id)

      FornecedorService.updateFornecedor(this.fornecedorManipulated, empresas)
        .then((response) => {
          console.log(response);
          let data = response.data;
          let elementToUpdate = this.myRowData.find((row) => row.id === data.id);

          elementToUpdate.id = data.id;
          elementToUpdate.cnpj = data.cnpj;
          elementToUpdate.cpf = data.cpf;
          elementToUpdate.nome = data.nome;
          elementToUpdate.email = data.email;
          elementToUpdate.cep = data.cep;
          elementToUpdate.is_pessoa_fisica = data.is_pessoa_fisica;
          elementToUpdate.rg = data.rg;
          elementToUpdate.data_nascimento = data.data_nascimento;

          elementToUpdate.empresas = empresas;
          elementToUpdate.listagemEmpresas = this.createListagemEmpresas(empresas);
          this.closeForm();
        })
        .catch((err) => {
          alert(err.response.data.message);
        });
    },
    displayForm() {

      EmpresaService.getEmpresas().then((response) => {
        this.dataEmpresasMarcados = JSON.parse(JSON.stringify(this.empresasMarcados(response)));
        document.getElementsByClassName("my-form")[0].style.display = "block";
      });
    },
    openFormEdit(data) {
      this.fornecedorManipulated.empresas = data.empresas;
      this.displayForm();
      
      this.fornecedorManipulated.id = data.id;
      this.fornecedorManipulated.cnpj = data.cnpj;
      this.fornecedorManipulated.cpf = data.cpf;
      this.fornecedorManipulated.nome = data.nome;
      this.fornecedorManipulated.email = data.email;
      this.fornecedorManipulated.cep = data.cep;
      this.fornecedorManipulated.is_pessoa_fisica = data.is_pessoa_fisica;
      this.fornecedorManipulated.rg = data.rg;
      this.fornecedorManipulated.data_nascimento = data.data_nascimento;

    },

    closeForm() {
      this.fornecedorManipulated.id = null;
      this.fornecedorManipulated.cnpj = "";
      this.fornecedorManipulated.cpf = null;
      this.fornecedorManipulated.nome = "";
      this.fornecedorManipulated.email = "";
      this.fornecedorManipulated.cep = null;
      this.fornecedorManipulated.is_pessoa_fisica = false;
      this.fornecedorManipulated.rg = "";
      this.fornecedorManipulated.data_nascimento = null;
      this.fornecedorManipulated.empresas = [];
      this.dataEmpresasMarcados = [];
      document.getElementsByClassName("my-form")[0].style.display = "none";

    },
    empresasMarcados(allEmpresas) {
      let empresasId = [];
      if (this.fornecedorManipulated.empresas) {
        for (let emp of this.fornecedorManipulated.empresas) {
          empresasId.push(Number(emp));
        }
      }
      for (let emp of allEmpresas.data) {
        emp.check = empresasId.includes(emp.id);
      }
      return allEmpresas.data;
    },
    createListagemEmpresas(empresasList) {

      let listagemEmpresas = "";
      for (let empresaIndex in empresasList) {
        let empresa = this.dataEmpresasMarcados[empresaIndex];
        listagemEmpresas += empresa.nomeFantasia + " - " +  empresa.cnpj + " ,";
      }
      return listagemEmpresas !== "" ? listagemEmpresas.substring(0, listagemEmpresas.length - 1) : "";
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
