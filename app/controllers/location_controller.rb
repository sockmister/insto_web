class LocationController < ApplicationController
	# Get all from faculty model. OR, hardcode since faculties hardly changes.
	def index
		@faculty = ["art", "biz", "den", "eng", "law", "med", "sci", "sde", "soc"]
		render :json => @faculty
	end
	
	# def new
	# end

	# def create
	# end

	# Get all with faculty == :id
	def show
		@all_locations = Location.where(:faculty => params[:id])
		render :json => @all_locations
	end

	# def edit
	# end

	# def update
	# end

	# def destroy
	# end	
end
