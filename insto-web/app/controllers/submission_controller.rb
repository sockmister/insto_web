class SubmissionController < ApplicationController
	def index
		if params[:user_id]
			@user = Submission.where(:user_id => params[:user_id])
			render :json => @user
		elsif params[:location_id]
			# sort by timestamp, and gleam factor (how?)
			@location = Submission.where(:location_id => params[:location_id]).order("created_at DESC, gleam DESC").limit(10)
			render :json => @location, :methods => :image_url
		end			
		# @submissions = Submission.all
		# render :json => @submissions
	end

	def new
		@submission = Submission.new
	end

	def create
		# respond_to :json
		# @submission = Submission.new(params[:submission])
		# if @submission.save
		# 	render :json => @submission
		# end
		respond_to :json
		@submission = Submission.new
		@submission.location_id = params[:location_id]
		@submission.user_id = params[:user_id]
		@submission.image = params[:image]
		if @submission.save
			render :json => @submission
		end
	end
end

# Started POST "/submission" for 127.0.0.1 at 2013-06-11 21:42:35 +0800
# Processing by SubmissionController#create as HTML
# Parameters: {"utf8"=>"✓", "authenticity_token"=>"rTjTmkMPkI9ONfdDb42mw/3Vt4hvVp7ca7aVQGuaXRA=", "submission"=>{"location_id"=>"1", "user_id"=>"1", "image"=>#<ActionDispatch::Http::UploadedFile:0x589fe0 @original_filename="8118747260_8b60f288f0_o.jpg", @content_type="image/jpeg", @headers="Content-Disposition: form-data; name=\"submission[image]\"; filename=\"8118747260_8b60f288f0_o.jpg\"\r\nContent-Type: image/jpeg\r\n", @tempfile=#<Tempfile:/tmp/RackMultipart20130611-4927-48lrmg>>}, "commit"=>"Submit"}