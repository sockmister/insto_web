class AddLocationIdToRequest < ActiveRecord::Migration
  def change
  	add_column :requests, :location_id, :integer
  end
end
