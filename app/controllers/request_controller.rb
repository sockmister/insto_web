class RequestController < ApplicationController
	# def index
	# end
	
	# def new
	# end

	# def create
	# end

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
