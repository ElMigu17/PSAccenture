  <script setup>
import { defineProps } from 'vue';

const cnpj = defineModel('cnpj');
const nomeFantasia = defineModel('nomeFantasia');
const cep = defineModel('cep');
const id = defineModel('id');

  const props = defineProps({
    dataFornecedoresMarcados: {
      type: Array,
      default: () => []
    }
  });

</script>

  <template>
    <div class="modal">
      <form class="form" v-on:submit.prevent="$emit('send-form')">
        <p>CNPJ:</p>
        <input type="text" v-model="cnpj" />
        <p>Nome Fantasia:</p>
        <input type="text" v-model="nomeFantasia" />
        <p>CEP:</p>
        <input placeholder="00000-000" maxlength="9" v-mask="'#####-###'" type="text" v-model="cep" />
        <p>Fornecedor:</p>
        <div v-if="props.dataFornecedoresMarcados && props.dataFornecedoresMarcados.length > 0">
          <label v-for="forn in props.dataFornecedoresMarcados" :key="forn.id" :for="forn.id">
            <input 
              type="checkbox"
              :value="forn.id"
              :id="forn.id" 
              :name="forn.id"
              :checked="forn.check"
              @click="forn.check = !forn.check"
            />
            {{ forn.nome }} - {{ forn.is_pessoa_fisica ? forn.cpf : forn.cnpj }} - {{ forn.check }} = {{ forn.id }}
            <br/>
          </label>
        </div>
        <br />
        <div class="buttons">
        <input class="button" type="submit" @click="$emit('edit-todo', event)" />
        <input class="close-button button" type="button" value="Close" @click="$emit('close', event)" />
      </div>
      </form>
    </div>
  </template>


  <script>


  export default {
  };
  </script>

  <style scoped>
  .modal {
    position: absolute;
    top: 0;
    left: 0;
    height: 100vh;
    width: 100vw;
    background-color: rgba(0, 0, 0, 0.3);
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .form {
    background-color: #FDFDFD;
    padding: 20px 30px;
    border-radius: 10px;
    border: 3px solid black;
  }

  .button {
    border-radius: 30px;
    margin-top: 15px;
    background-color: black;
    color: white;
    border: 2px solid black;
    padding: 3px 8px;
    cursor: pointer;
  }

  .buttons{
    display: flex;
    justify-content: space-around;
    width: 100%;

  }
  </style>
