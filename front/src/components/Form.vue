<script setup>

const cnpj = defineModel('cnpj');
const nomeFantasia = defineModel('nomeFantasia');
const cep = defineModel('cep');
</script>

<template>
    <div class="modal">
      <form class="form" v-on:submit.prevent="$emit('send-form')">
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
        /> <br/>
        <input class="submit-button" type="submit" @click="$emit('edit-todo')" />
      </form>
    </div>
</template>


<script>


export default {
  props: {
      cnpj: {
        type: String,
      },
      nomeFantasia: {
        type: String,
      },
      cep: {
        type: Number,
      },
  }
};
</script>

<style scoped>





.modal{
  position: absolute;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100vw;
  background-color: rgba(0,0,0,0.3);
  display: flex;
  justify-content: center;
  align-items: center;
}

.form{
  background-color: #FDFDFD;
  padding: 20px 30px;
  border-radius: 10px;
  border: 3px solid black;
}

.submit-button{
  border-radius: 30px;
  margin-top: 15px;
  background-color: black;
  color: white;
  border: 1px solid black;
}
</style>
