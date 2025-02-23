<template>
  <button
    :class="['custom-button']"
    @click="handleClick"
  >{{this.params.label}}
  </button>
</template>

<script>
export default {
  name: 'TableButton',
  methods: {
    handleClick() {
      console.log(this.params);
      console.log("data", this.params.data);
      this.params.onClick(this.params.data);
    },
  },
};
</script>

<style scoped>
.custom-button {
  padding: 10px 20px;
  font-size: 16px;
  color: white;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.custom-button:hover {
  background-color: #0056b3;
}

.custom-button:active {
  background-color: #004080;
}

</style>
