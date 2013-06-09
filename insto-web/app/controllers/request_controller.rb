class RequestController < ApplicationController
	before_filter :get_parent

	def get_parent
		if params[:user_id]
      @parent = User.find(params[:user_id])
    elsif params[:location_id]
      @parent = Location.find(params[:location_id])
    else
      # handle this case however is appropriate to your application...
    end
	end

	def index
		@request = @parent
		render :json => @request
	end

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

	def show
	end

	# def edit
	# end

	# def update
	# end

	# def destroy
	# end	

	def top
		# get top 50 requests sorted by count
		@top_request = Request.find_by_sql("
			SELECT l.faculty AS faculty, l.id AS location_id, l.name AS location_name, count(*) as request_count
			FROM locations l, requests r
			WHERE l.id = r.location_id
			GROUP BY location_name
			ORDER BY request_count DESC
			LIMIT 0, 10
			")
		# @top_request = Request.all
		render :json => @top_request
	end
end
