class SubmissionController < ApplicationController
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
