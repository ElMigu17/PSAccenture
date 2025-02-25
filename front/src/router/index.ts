import { createRouter, createWebHistory } from 'vue-router';
import Empresa from "../views/Empresa.vue";

import { ModuleRegistry } from 'ag-grid-community'; 
import { ClientSideRowModelModule } from 'ag-grid-community'; 

ModuleRegistry.registerModules([ ClientSideRowModelModule ]); 
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'empresa',
      component: Empresa,
    },
    {
      path: '/fornecedor',
      name: 'Fornecedor',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/Fornecedores.vue'),
    },
  ],
})

export default router
