  <script setup>
  import { defineProps } from 'vue';

  const fornecedorManipulated = defineModel('fornecedorManipulated');

  const props = defineProps({
    dataEmpresasMarcados: {
      type: Array,
      default: () => []
    }
  });

</script>

  <template>
    <div class="modal">
      <form class="form" v-on:submit.prevent="$emit('send-form')">
        <p>CNPJ:</p>
        <input type="text" v-model="fornecedorManipulated.cnpj" />
        <p>CPF:</p>
        <input type="text" v-model="fornecedorManipulated.cpf" />
        <p>Nome:</p>
        <input type="text" v-model="fornecedorManipulated.nome" />
        <p>E-mail:</p>
        <input type="text" v-model="fornecedorManipulated.email" />
        <p>CEP:</p>
        <input placeholder="00000-000" maxlength="9" v-mask="'#####-###'" type="text" v-model="fornecedorManipulated.cep" />
        <br/>
        <label>
          <input type="checkbox" v-model="fornecedorManipulated.is_pessoa_fisica"
             />
            Pessoa Fisica
        </label>
        <p>RG:</p>
        <input type="text" v-model="fornecedorManipulated.rg" />
        <p>Data de Nascimento:</p>
        <input type="text" v-model="fornecedorManipulated.data_nascimento" />
        <p>Empresas:</p>
        <div v-if="props.dataEmpresasMarcados && props.dataEmpresasMarcados.length > 0">
          <input type="text" v-model="filtro" />
          <div v-for="emp in props.dataEmpresasMarcados">
            <label
              v-if="filtro === '' || filtro === null || filtro !==null && (emp.cnpj.includes(filtro) || emp.nome.includes(filtro))"
              :key="emp.id" :for="emp.id">
              <input type="checkbox" :value="emp.id" :id="emp.id" :name="emp.id" :checked="emp.check"
                @click="emp.check = !emp.check" />
              {{ emp.nomeFantasia }} - {{ emp.cnpj }}
              <br />
            </label>
          </div>
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
  data() {
    return {
      filtro: null
    }
  }
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
  z-index: 90;
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

.buttons {
  display: flex;
  justify-content: space-around;
  width: 100%;

}
</style>
