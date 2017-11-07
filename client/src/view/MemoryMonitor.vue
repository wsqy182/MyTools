<template>
  <div>
    <h1>内存监控台</h1>
    <el-row>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>当前用户列表</span>
          <el-button style="float: right; padding: 3px 0" type="text" v-on:click="refresh()">刷新</el-button>
        </div>
        <el-table
          :data="tableData"
          style="width: 100%" border="true">
          <el-table-column
            align="left"
            prop="userId"
            label="用户ID"
            width="180">
          </el-table-column>
          <el-table-column
            align="left"
            prop="username"
            label="用户名"
            width="180">
          </el-table-column>
          <el-table-column
            align="left"
            prop="nickname"
            label="昵称">
          </el-table-column>
        </el-table>
      </el-card>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>当前token列表</span>
          <el-button style="float: right; padding: 3px 0" type="text" v-on:click="refresh2()">刷新</el-button>
        </div>
        <el-table
          :data="tableData2"
          style="width: 100%" border="true">
          <el-table-column
            align="left"
            prop="userId"
            label="用户ID"
            width="50">
          </el-table-column>
          <el-table-column
            align="left"
            prop="token"
            label="token"
            :formatter="tokenFormatter"
            width="180">
          </el-table-column>
          <el-table-column
            align="left"
            prop="createDate"
            label="创建时间"
            :formatter="dateFormatter"
            widtd="80">
          </el-table-column>
          <el-table-column
            align="left"
            prop="validTime"
            :formatter="timeFormatter"
            label="有效时长(min)"
            width="60">
          </el-table-column>
        </el-table>
      </el-card>
    </el-row>
  </div>
</template>
<script>
  export default {
    name: '',
    data() {
      return {
        msg: '',
        tableData: [],
        tableData2: []
      }
    },
    methods: {
      refresh: function () {
        this.$ajax.get(
          this.$apilist.seeUser()
        ).then(res => {
          console.log(res.data);
          this.tableData = res.data.data;
        }).catch(err => {
          console.log(err)
        })
      },
      refresh2: function () {
        this.$ajax.get(
          this.$apilist.seeToken()
        ).then(res => {
          console.log(res.data);
          this.tableData2 = res.data.data;
        }).catch(err => {
          console.log(err)
        })
      },
      dateFormatter: function (row, column, cellValue) {
        var date = new Date();
        date.setTime(cellValue);
        return (date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + "" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
      }, tokenFormatter: function (row, column, cellValue) {
        return cellValue.substring(0, 5) + "...";
      }, timeFormatter: function (row, column, cellValue) {
        return cellValue / 60 / 1000;
      }
    },
    mounted: function () {
      this.$nextTick(function () {
        this.refresh();
        this.refresh2();
        // Code that will run only after the
        // entire view has been rendered
      })
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
    /*width: 500px;*/
  }
</style>
