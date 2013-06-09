class SubmissionController < ApplicationController
	def index
		if params[:user_id]
			@user = Submission.where(:user_id => params[:user_id])
			render :json => @user
		elsif params[:location_id]
			@location = Submission.where(:location_id => params[:location_id])
			render :json => @location
		end			
		# @submissions = Submission.all
		# render :json => @submissions
	end

	def new
		@submission = Submission.new
	end

	def create
		@submission = Submission.new(params[:submission])
		if @submission.save
			render :json => @submission
		end

	end
end
