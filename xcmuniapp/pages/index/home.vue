<template>
	<view class="context" >
		<view v-if="!hasLogin" class="userinfo">
			<view class="btn-row" style="width: 100%;margin:400rpx 0 0 0">
				<button type="primary"   @tap="bindLogin">登录</button>
			</view>
		</view>
		<view v-else class="userinfo">
			<image :src="userInfo.avatarUrl" class="userinfo-avatar"></image>
			<view class="userinfo-nickname">姓名：{{userInfo.nickName}}</view>
			<view style="margin:80rpx 0 0 0;">
				<view class="uni-form-item uni-column">
					<radio-group name="radio" @change="radioChange">
						<label>
							<radio value="0" :checked="data.kind==0"/><text>支出</text>
						</label>
						<label style="margin-left:80rpx">
							<radio value="1" :checked="data.kind==1"/><text>收入</text>
						</label>
					</radio-group>
				</view>
				<view class="uni-form-item uni-column" style="margin-top:80rpx">
					金额<input class="uni-input" v-model="data.payments" @input="paymentsInput" type="digit" placeholder="$_$" />
				</view>
				<view class="uni-list-cell-db" style="margin-top:80rpx">
					种类
					<picker @change="bindPickerChange" v-model="data.type" :range="array">
						<view class="uni-input">{{array[data.type]}}</view>
					</picker>
				</view>
				<view style="margin-top:80rpx">
					<view class="uni-form-item uni-column">
						<view class="title">内容</view>
						<input class="uni-input" v-model="data.context" @input="contextInput" focus placeholder="收支内容" />
					</view>
				</view>
				<!-- <view class="btn-row" style="display: flex;margin-top:80rpx;"> -->
			</view>
			<view class="btn-row" style="margin-top:80rpx;width: 100%;">
				<button type="primary" @tap="bindAddBalance" style="width: 100%;">提交</button>
				<button type="default" @tap="bindLogout" style="margin-top:10rpx;">退出</button>
			</view>
		</view>
	</view>	
</template>

<script>
	import {mapState,mapActions} from 'vuex';
	export default {
		computed: {
			...mapState(['userInfo','hasLogin']),
		},
		data() {
			return {
				title: 'Hello',
				array: ['衣服', '美食', '外卖', '购物','收入','其他'],
				index:0,
				data:{
					kind:0,
					payments:'',
					type:0,
					context:''
				}
			}
		},
		onLoad() {

		},
		methods: {
			...mapActions(['logout']),
			radioChange(e){
				this.data.kind = e.target.value
			},
			paymentsInput(e){
				this.data.payments = e.detail.value;
			},
			contextInput(e){
				this.data.context = e.detail.value;
			},
			bindAddBalance(){
				uni.request({
					url: 'https://106.14.223.79/wx/addBalance',
					method: 'POST',
				    data: this.data,
				    success: (res) => {
						if(res.data.status == 200){
							this.data.kind=0
							this.data.payments=''
							this.data.type=0
							this.data.context=''
							uni.showToast({
								title:"添加成功" 
							})
						}else{
							uni.showToast({
								title:"添加失败" 
							})
						}
						
				    }
				});
			},
			bindLogin() {
				uni.reLaunch({url: '/pages/index/login'});
			},
			bindLogout() {
				this.$store.dispatch('logout').then(() => {
					uni.reLaunch({url: '/pages/index/index'});
				}).catch((error) => {
					if (error !== 'error') {
						uni.showToast({title: error,icon: "none"});
					}
				})
			},
			bindPickerChange: function(e) {
				console.log('picker发送选择改变，携带值为', e.target.value)
				this.data.type = e.target.value
			},
		}
	}
</script>

<style>
	.container {
	  width: 100%;
	  display: flex;
	  flex-direction: column;
	  align-items: center;
	  justify-context: space-between;
	  padding: 200rpx 0;
	  box-sizing: border-box;
	}
	
	.userinfo {
	  display: flex;
	  flex-direction: column;
	  align-items: center;
	}
	
	.userinfo-avatar {
	  width: 128rpx;
	  height: 128rpx;
	  margin: 20rpx;
	  border-radius: 50%;
	}

	.userinfo-nickname {
	  color: #aaa;
	}
</style>
