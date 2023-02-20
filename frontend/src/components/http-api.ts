import axios, { AxiosResponse } from 'axios';
import { Ref, ref } from 'vue';


export async function getImages(images: Ref<any>) {
    axios.get('/images').then(function (res) {
        images.value = res.data;
    })
}

export async function DownloadImage(e: any) {
    const imageUrl = "/images/" + e.target.value;
    return axios.get(imageUrl, { responseType: "blob" })
      .then(function (response: AxiosResponse) {
        const reader = new window.FileReader();
        reader.readAsDataURL(response.data);
        return new Promise((resolve, reject) => {
          reader.onload = function () {
            const imageDataUrl = (reader.result as string);
            resolve(imageDataUrl);
          }
        });
      });
  }
  
  export async function DownloadGalerie(e:any) {
    const imageUrl = "/images/" + e;
    return axios.get(imageUrl, { responseType: "blob" })
      .then(function (response: AxiosResponse) {
        const reader = new window.FileReader();
        reader.readAsDataURL(response.data);
        return new Promise((resolve, reject) => {
          reader.onload = function () {
            const imageDataUrl = (reader.result as string);
            resolve(imageDataUrl);
          }
        });
      });
  }
  export async function submitFile(file:any){
    console.log(file);
    let formData = new FormData();
    formData.append('file', file);
    axios.post( '/images', formData,{ headers: { 'Content-Type': 'multipart/form-data' }}).then(function(){ console.log('SUCCESS!!');}).catch(function(){
    console.log('FAILURE!!');
    });
    }