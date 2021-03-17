<template>
	<view class="content" >
		<view v-if="!hasLogin" class="userinfo">
			<view class="btn-row" style="width: 100%;margin:400rpx 0 0 0">
				<button type="primary"   @tap="bindLogin">登录</button>
			</view>
		</view>
		<view v-else class="userinfo">
			<image :src="userInfo.avatarUrl" class="userinfo-avatar"></image>
			<view class="userinfo-nickname">姓名：{{userInfo.nickName}}</view>
			<view style="margin:80rpx 0 0 50%;width: 100%;">
				<view style="margin-top: 80rpx;display: flex;">
					<view>收：</view>
					<view>￥{{data.income}}</view>
				</view>
				<view style="display: flex;margin-top: 80rpx;">
					<view>支：</view>
					<view>￥{{data.outcome}}</view>
				</view>
				<view style="display: flex;margin-top: 80rpx;">
					<view>合：</view>
					<view>￥{{data.total}}</view>
				</view>
				<!-- <view class="btn-row" style="display: flex;margin-top:80rpx;"> -->
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
				data:{
					income: 100.1,
					outcome:2.1,
					total:300
				}
			}
		},
		onShow() {
			uni.request({
				url: 'https://106.14.223.79/wx/getTotal',
				success: (res) => {
					console.log(res.data)
					if(res.data.status == 200){
						this.data = res.data.obj;
					}
				}
			});
		},
		methods: {
			...mapActions(['logout']),

			bindLogin() {
				uni.reLaunch({url: '/pages/index/login'});
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
	  justify-content: space-between;
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
