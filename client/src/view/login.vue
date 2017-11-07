<template>
  <div>
    <el-row type="flex" class="row-bg" justify="center">
      <el-card class="box-card">
        <div style="margin: 20px;"></div>
        <el-form :label-position="labelPosition" label-width="80px" :model="form">
          <el-form-item label="帐号">
            <el-input v-model="form.accounts"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input type="password" v-model="form.password"></el-input>
          </el-form-item>
          <el-button size="medium" v-on:click="login">中等按钮</el-button>
        </el-form>
      </el-card>
    </el-row>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        labelPosition: 'right',
        form: {
          accounts: '',
          password: '',
        }
      }
    }, methods: {
      login: function (event) {
        this.$ajax.get(this.$apilist.userLogin(), this.form
        ).then(
          res => {
            console.log(res.data);
            if (res.data.code == 200) {
              this.$message(JSON.stringify(res.data.data));
            } else {
              this.$message(res.data.msg);
            }
          }
        ).catch(err => {
          console.log(err);
        })
      }
    }
  }
</script>

<style scoped>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 500px;
    align: center
  }
</style>
