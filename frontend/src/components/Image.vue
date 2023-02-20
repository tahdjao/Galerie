<template>
  <div class="imagesmenu">
    <select v-model="selectedImage" @change="displaySelectedImage">
      <option v-for="img in images" :value="img.id" class="image">
        {{ img.name }}
      </option>
    </select>
    <img :src="selectedImageData" id="selected">
  </div>
</template>
<script lang="ts">
import { ref } from 'vue'
import axios, { AxiosResponse } from 'axios'
import { getImages, DownloadImage } from './http-api'

export default {
  setup() {
    const images = ref()
    const selectedImage = ref(null)
    const selectedImageData = ref()

    getImages(images)

    async function displaySelectedImage(selectedImage: any) {
      const imageData = await DownloadImage(selectedImage)
      selectedImageData.value = imageData
    }

    return {
      images,
      selectedImage,
      selectedImageData,
      displaySelectedImage
    }
  }
}

</script>
<style>
  .imagesmenu {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    width: 50%;
    float: left;
  }
  select {
    font-size: 22px;
    color: #000;
    background-color: #f2f2f2;
    border: none;
    border-radius: 20px;
    padding: 10px 20px;
    margin: 20px 0;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.25);
  }
  option {
    font-size: 18px;
    color: #000;
  }
  img#selected {
    width: 60%;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.25);
  }

</style>
