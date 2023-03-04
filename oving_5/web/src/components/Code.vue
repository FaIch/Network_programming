<template>
    <div class="container">
        <h3>Input your Python code here:</h3>
        <textarea id="input_field" v-model="code_input"></textarea>
        <button id="submit" type="button" @click="submit">Run</button>
    </div>

    <div class="container">
        <h3>Compiled code:</h3>
        <div id="output_field">
            <p>{{ code_output }}</p>
        </div>
    </div>
</template>

<script>
import { compileCode} from '../utils/httputils'

export default{
    data() {
        return{
            code_input: "",
            code_output: ""
        }
    },
    
    methods: {
        async submit() {
            compileCode(this.code_input).then(
                (response) =>{
                    this.code_output = response.data.output;
                }
            ).catch((error)=> {
                alert(error.response)
            });
        }
    }
}
</script>

<style scoped>
.container{
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    color: white;
}


#submit{
    margin-top: 10px;
    width: 100px;
    background-color: white;
    border-radius: 20px;
}

#output_field, #input_field{
    background-color: white;
    color: black;
    width: 100%;
}


</style>