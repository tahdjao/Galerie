<script lang="ts">
import { onMounted, Ref, ref } from 'vue';
import { getImages, DownloadImage, DownloadGalerie, submitFile } from './http-api';

export default {
  setup() {
    const images =ref()
    const selectedImageData = ref('')
    let dataUrl:any
    onMounted(async () => {
      await getImages(images);
    });

    async function handleDownloadImage(img: any) {
      dataUrl = await DownloadGalerie(img.id);
      const imageEl=document.getElementById(img.id)
      if (imageEl!=null){
        imageEl.setAttribute("src", dataUrl);
      }
      
    }

    return {
        images,
      selectedImageData,
      handleDownloadImage
    };
  }
};
</script>
<template>
  <div  v-for="img in images" class="galerie">
    <img :id="img.id" :ref="handleDownloadImage" class="galerieimg">
  </div>
</template>
<style>
  .galerie {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    width: 50%;
    float: right;
  }
  .galerie img {
    height: 200px;
    width: 200px;
    margin: 10px;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.25);
  }
</style>



