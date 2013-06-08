class UserController < ApplicationController
	# def index
	# end
	
	# def new
	# end

	# def create
	# end

	def show
		@user = User.find_by_user(params[:id])
		render :json => @user
	end

	# def edit
	# end

	# def update
	# end

	# def destroy
	# end	
end
