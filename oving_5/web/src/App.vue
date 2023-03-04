<template>
  <body>
    <Code></Code>
  </body>
</template>

<script>
import Code from './components/Code.vue'

export default {
  name: 'App',
  components: {
    Code
  }
}
</script>

<style>
body{
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: black;
}

</style>
