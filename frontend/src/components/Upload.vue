
<template>
  <div class="container">
    <div class="large-12 medium-12 small-12 cell">
      <label>File
        <input type="file" id="file" ref="file" v-on:change="handleFileUpload"/>
      </label>
      <button @click="submit">Submit</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { submitFile, getImages } from './http-api';

let file = ref(File)
let images=ref()

function handleFileUpload( event:any ){
  file = event.target.files[0];
}
function submit(){
  submitFile(file)
}

</script> 