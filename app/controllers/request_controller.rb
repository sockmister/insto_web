class RequestController < ApplicationController
	# def index
	# end

	# only for testing
	def new
		@request = Request.new
	end

	def create
		# @new_request = Request.new
		# # do some checking please
		# @user = User.where(:user => params[:user_id])
		# if @user.empty?
		# 	render :json => @new_request
		# else
		# 	@new_request.user_id = @user[0].id
		# 	@new_request.location = params[:location]
		# 	@new_request.save
		# 	render :json => @new_request
		# end
		# respond_to :json
		@request = Request.new(params[:request])
		if @request.save
			render :json => @request
		end
	end

	# def show
	# end

	# def edit
	# end

	# def update
	# end

	# def destroy
	# end	

	def top
		# get top 50 requests sorted by count
		@top_request = Request.select("location as location, count(*) as request_count")
													.group("location").order("request_count DESC").limit(20)
		render :json => @top_request
	end
end
