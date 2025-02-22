<template>
  <div class="greetings">
    <p>CNPJ:</p>
    <input v-model="message" placeholder="edit me" />
  </div>
</template>


<script>
import { AgGridVue } from "ag-grid-vue3";
import axios from 'axios';
import { themeAlpine } from 'ag-grid-community';


export default {
};
</script>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
